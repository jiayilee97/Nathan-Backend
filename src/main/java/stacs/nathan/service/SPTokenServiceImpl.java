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
import stacs.nathan.dto.request.SPTokenRequestDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.BaseCurrencyToken;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;
import stacs.nathan.repository.SPTokenRepository;
import stacs.nathan.utils.enums.SPTokenStatus;
import stacs.nathan.utils.enums.TokenType;

import java.util.List;

@Service
public class SPTokenServiceImpl implements SPTokenService {
  private static final Logger LOGGER = LoggerFactory.getLogger(SPTokenServiceImpl.class);

  @Autowired
  SPTokenRepository repository;

  @Autowired
  UserService userService;

  @Autowired
  BlockchainService blockchainService;

  public void createSPToken(SPTokenRequestDto dto) throws ServerErrorException {
    LOGGER.debug("Entering createSPToken().");
    try{
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      //String username = "username_test";
      User loggedInUser = userService.fetchByUsername(username);
      JsonRespBO jsonRespBO = blockchainService.createToken(loggedInUser, TokenType.BC_TOKEN, dto.getTokenCode(), dto.getNotionalAmount());

      // jsonRespBO.getTxId() returns a json string. Need to parse it to extract the txid
      JsonParser parser = new JsonParser();
      JsonObject txResponse = (JsonObject) parser.parse(jsonRespBO.getTxId());
      String txId = txResponse.get("txId").getAsString();
      TokenQueryRespBO txDetail = blockchainService.getTxDetails(txId);
      //System.out.println("Token name: " + txDetail.getTokenInfo().getTokenCode());
      SPToken token = convertToSPToken(dto);
      token.setCtxId(txDetail.getTxId());
      token.setBlockHeight(txDetail.getBlockHeight());
      repository.save(token);
    }catch (Exception e){
      LOGGER.error("Exception in createBCToken().", e);
      throw new ServerErrorException("Exception in createBCToken().", e);
    }
  }

  public SPToken convertToSPToken(SPTokenRequestDto dto){
    SPToken token = new SPToken();
    token.setUnderlyingCurrency(dto.getUnderlyingCurrency());
    token.setTokenCode(dto.getTokenCode());
    return token;
  }

  public List<SPTokenResponseDto> fetchAllOpenPositions(User user){
    return repository.fetchAllOpenPositions(user, SPTokenStatus.ACTIVE);
  }

  public List<SPTokenResponseDto> fetchAllClosedPositions(User user){
    return repository.fetchAllClosedPositions(user, SPTokenStatus.ACTIVE);
  }

}
