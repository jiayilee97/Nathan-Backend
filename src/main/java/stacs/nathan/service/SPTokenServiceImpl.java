package stacs.nathan.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hashstacs.sdk.response.base.JsonRespBO;
import hashstacs.sdk.response.blockchain.TokenQueryRespBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.SPTokenRequestDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
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
      //String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      String username = "usernamew_test";
      User loggedInUser = userService.fetchByUsername(username);
      JsonRespBO jsonRespBO = blockchainService.createToken(loggedInUser, TokenType.BC_TOKEN, dto.getNotionalAmount());

      // jsonRespBO.getTxId() returns a json string. Need to parse it to extract the txid
      JsonParser parser = new JsonParser();
      JsonObject txResponse = (JsonObject) parser.parse(jsonRespBO.getTxId());
      String txId = txResponse.get("txId").getAsString();
      Thread.sleep(5000);
      TokenQueryRespBO txDetail = blockchainService.getTxDetails(txId);
      if (txDetail != null) {
        SPToken token = convertToSPToken(dto);
        token.setUser(loggedInUser);
        token.setCtxId(txDetail.getTxId());
        token.setBlockHeight(txDetail.getBlockHeight());
        token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
        token.setIssuingAddress(loggedInUser.getWalletAddress());
        repository.save(token);
      }
    }catch (Exception e){
      LOGGER.error("Exception in createSPToken().", e);
      throw new ServerErrorException("Exception in createSPToken().", e);
    }
  }

  public SPToken convertToSPToken(SPTokenRequestDto dto){
    SPToken token = new SPToken();
    token.setUnderlyingCurrency(dto.getUnderlyingCurrency());
    token.setTokenCode(dto.getTokenCode());
    token.setContractInceptionDate(dto.getContractInceptionDate());
    token.setCpId(dto.getCounterPartyId());
    token.setOpsId(dto.getOpsId());
    token.setMaturityDate(dto.getMaturityDate());
    token.setFixingPage(dto.getFixingPage());
    token.setFixingAmount(dto.getAmountPerFixing());
    token.setNumberOfFixing(dto.getNumFixing());
    token.setKnockOutPrice(dto.getKnockoutPrice());
    token.setProductType(dto.getProductType());
    token.setSpotPrice(dto.getIndicativeSpotPrice());
    token.setStrikeRate(dto.getStrikeRate());
    token.setNotionalAmount(dto.getNotionalAmount());
    token.setStatus(SPTokenStatus.ACTIVE);
    return token;
  }

  public List<SPTokenResponseDto> fetchAllOpenPositions(User user){
    return repository.fetchAllOpenPositions(user, SPTokenStatus.ACTIVE);
  }

  public List<SPTokenResponseDto> fetchAllClosedPositions(User user){
    return repository.fetchAllClosedPositions(user, SPTokenStatus.ACTIVE);
  }

}
