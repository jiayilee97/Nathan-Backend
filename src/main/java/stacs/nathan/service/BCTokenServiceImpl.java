package stacs.nathan.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hashstacs.sdk.response.base.JsonRespBO;
import hashstacs.sdk.response.blockchain.TokenQueryRespBO;
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
      JsonRespBO jsonRespBO = blockchainService.createToken(loggedInUser, TokenType.BC_TOKEN, dto.getAmount());
      JsonParser parser = new JsonParser();
      JsonObject txResponse = (JsonObject) parser.parse(jsonRespBO.getTxId());
      String txId = txResponse.get("txId").getAsString();
      BaseCurrencyToken token = convertToBCToken(dto);
      token.setUser(loggedInUser);
      token.setCtxId(txId);
      token.setIssuerId(loggedInUser.getUuid());
      token.setIssuerAddress(loggedInUser.getWalletAddress());
      repository.save(token);
      Thread.sleep(5000);
      TokenQueryRespBO txDetail = blockchainService.getTxDetails(txId);
      // retry if not success
      token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
      token.setBlockHeight(txDetail.getBlockHeight());
      repository.save(token);
    }catch (Exception e){
      LOGGER.error("Exception in createBCToken().", e);
      throw new ServerErrorException("Exception in createBCToken().", e);
    }
  }

  public List<BCTokenResponseDto> fetchAllByIssuerAddress(String issuerAddress) throws ServerErrorException {
    LOGGER.debug("Entering fetchAllBCTokens().");
    try{
//      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
//      User loggedInUser = userService.fetchByUsername(username);
      return repository.fetchAllByIssuerAddress(issuerAddress);
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
