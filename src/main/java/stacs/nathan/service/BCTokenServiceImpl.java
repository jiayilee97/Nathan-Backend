package stacs.nathan.service;

import hashstacs.sdk.response.base.JsonRespBO;
import hashstacs.sdk.response.blockchain.TxDetailRespBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.response.BCTokenResponseDto;
import stacs.nathan.entity.BaseCurrencyToken;
import stacs.nathan.entity.User;
import stacs.nathan.repository.BCTokenRepository;
import stacs.nathan.utils.enums.TokenType;

import java.util.List;

@Service
public class BCTokenServiceImpl implements BCTokenService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BCTokenServiceImpl.class);

  @Autowired
  BCTokenRepository repository;

  @Autowired
  UserService userService;

  @Autowired
  BlockchainService blockchainService;

  public void createBCToken(BCTokenRequestDto dto) throws ServerErrorException {
    LOGGER.debug("Entering createBCToken().");
    try{
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      JsonRespBO jsonRespBO = blockchainService.createToken(loggedInUser, TokenType.BC_TOKEN, dto.getTokenCode(), dto.getAmount());
      TxDetailRespBO txDetail = blockchainService.getTxDetails(jsonRespBO.getTxId());
      BaseCurrencyToken token = convertToBCToken(dto);
      token.setCtxId(txDetail.getTxId());
      token.setBlockHeight(txDetail.getBlockHeight());
//      token.setTokenContractAddress(jsonRespBO.);
      repository.save(token);
    }catch (Exception e){
      LOGGER.error("Exception in createBCToken().", e);
      throw new ServerErrorException("Exception in createBCToken().", e);
    }
  }

  public List<BCTokenResponseDto> fetchAllBCTokens() throws ServerErrorException {
    LOGGER.debug("Entering fetchAllBCTokens().");
    try{
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      return repository.fetchAllByIssuerAddress(loggedInUser.getWalletAddress());
    }catch (Exception e){
      LOGGER.error("Exception in fetchAllBCTokens().", e);
      throw new ServerErrorException("Exception in fetchAllBCTokens().", e);
    }
  }

  public BaseCurrencyToken convertToBCToken(BCTokenRequestDto dto){
    BaseCurrencyToken token = new BaseCurrencyToken();
    token.setUnderlyingCurrency(dto.getUnderlyingCurrency());
    token.setTokenCode(dto.getTokenCode());
    token.setCurrencyCode(dto.getCurrencyCode());
    token.setAmount(dto.getAmount());
    return token;
  }

}
