package stacs.nathan.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hashstacs.sdk.response.base.JsonRespBO;
import hashstacs.sdk.response.blockchain.token.TokenQueryRespBO;
import hashstacs.sdk.response.blockchain.token.TransferQueryRespBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.request.SPTokenRequestDto;
import stacs.nathan.dto.response.CreateSPTokenInitDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.Balance;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;
import stacs.nathan.repository.SPTokenRepository;
import stacs.nathan.utils.enums.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
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

  @Autowired
  CodeValueService codeValueService;

  @Autowired
  BalanceService balanceService;

  @Value("${stacs.burn.address}")
  String burnAddress;

  @Transactional(rollbackFor = ServerErrorException.class)
  public void createSPToken(SPTokenRequestDto dto) throws ServerErrorException {
    LOGGER.debug("Entering createSPToken().");
    try{
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      SPTokenResponseDto responseDto = repository.findByTokenCode(dto.getTokenCode());
      if (responseDto != null) {
        throw new Exception("Token code already exists");
      }
      SPToken token = convertToSPToken(dto);
      token.setOpsId(String.valueOf(loggedInUser.getId()));
      token.setUser(loggedInUser);
      token.setIssuingAddress(loggedInUser.getWalletAddress());
      token.setCreatedBy(username);
      token.setStatus(SPTokenStatus.UNCONFIRMED_IN_CHAIN);
      JsonRespBO jsonRespBO = blockchainService.createToken(loggedInUser, TokenType.SP_TOKEN, dto.getNotionalAmount());
      if (jsonRespBO == null) {
        token.setStatus(SPTokenStatus.CHAIN_UNAVAILABLE);
        repository.save(token);
      } else {
        processAvailableChain(token, jsonRespBO);
      }
    }catch (Exception e){
      LOGGER.error("Exception in createSPToken().", e);
      throw new ServerErrorException("Exception in createSPToken().", e);
    }
  }

  public SPToken convertToSPToken(SPTokenRequestDto dto) {
    SPToken token = new SPToken();
    token.setClientId(dto.getClientId());
    token.setUnderlyingCurrency(dto.getUnderlyingCurrency());
    token.setTokenCode(dto.getTokenCode());
    token.setContractInceptionDate(dto.getContractInceptionDate());
    token.setCpId(dto.getCounterPartyId());
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

  public List<SPTokenResponseDto> fetchAllOpenPositions(User user) {
    return repository.fetchAllOpenPositions(user, SPTokenStatus.ACTIVE);
  }

  public List <SPTokenResponseDto> fetchAllTokens(User user) {
    return repository.fetchAllTokens(user);
  }

  public List<SPTokenResponseDto> fetchAllClosedPositions(User user) {
    return repository.fetchAllClosedPositions(user, Arrays.asList(SPTokenStatus.CONTRACT_MATURITY, SPTokenStatus.KNOCK_OUT));
  }

  public CreateSPTokenInitDto fetchInitForm() {
    String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    User loggedInUser = userService.fetchByUsername(username);
    CreateSPTokenInitDto dto = new CreateSPTokenInitDto();
    dto.setClientIds(userService.fetchAllClientIds());
    dto.setProductType(ProductType.getValuesSelection());
    dto.setUnderlying(codeValueService.findByType(CodeType.UNDERLYING));
    dto.setIssuingAddress(loggedInUser.getWalletAddress());
    return dto;
  }

  public SPTokenResponseDto fetchByTokenCode(String tokenCode) {
    return repository.findByTokenCode(tokenCode);
  }

  public void executeUnconfirmedChain() {
    LOGGER.debug("Entering executeUnconfirmedChain().");
    try {
      List<SPToken> tokens = repository.findByStatus(SPTokenStatus.UNCONFIRMED_IN_CHAIN);
      for(SPToken token : tokens){
        TokenQueryRespBO txDetail = blockchainService.getTxDetails(token.getCtxId());
        if(txDetail != null) {
          token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
          token.setBlockHeight(txDetail.getBlockHeight());
          token.setStatus(SPTokenStatus.ACTIVE);
          repository.save(token);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Exception in executeUnconfirmedChain().", e);
    }
  }

  public void executeUnavailableChain() {
    LOGGER.debug("Entering executeUnavailableChain().");
    try {
      List<SPToken> tokens = repository.findByStatus(SPTokenStatus.CHAIN_UNAVAILABLE);
      for(SPToken token : tokens){
        JsonRespBO jsonRespBO = blockchainService.createToken(token.getUser(), TokenType.SP_TOKEN, token.getNotionalAmount());
        if (jsonRespBO != null) {
          processAvailableChain(token, jsonRespBO);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Exception in executeUnavailableChain().", e);
    }
  }

  void processAvailableChain(SPToken token, JsonRespBO jsonRespBO){
    JsonParser parser = new JsonParser();
    JsonObject txResponse = (JsonObject) parser.parse(jsonRespBO.getTxId());
    String txId = txResponse.get("txId").getAsString();
    token.setCtxId(txId);
    token.setStatus(SPTokenStatus.UNCONFIRMED_IN_CHAIN);
    repository.save(token);
    TokenQueryRespBO txDetail = blockchainService.getTxDetails(txId);
    if (txDetail != null) {
      token.setBlockHeight(txDetail.getBlockHeight());
      token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
      token.setStatus(SPTokenStatus.ACTIVE);
      repository.save(token);
      Balance balance = new Balance();
      balance.setUser(token.getUser());
      balance.setTokenType(TokenType.SP_TOKEN);
      balance.setTokenCode(token.getTokenCode());
      balance.setBalanceAmount(token.getNotionalAmount());
      balanceService.createBalance(balance);
    }
  }

  public void transferToBurnAddress(String tokenCode) throws ServerErrorException {
    LOGGER.debug("Entering transferToBurnAddress().");
    try {
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      SPToken token = repository.findSPTokenByTokenCode(tokenCode);
      //TransactionHistory transaction = initTransactionHistory(token);
      //retrieve balance and transfer all
      JsonRespBO jsonRespBO = blockchainService.transferToken(loggedInUser, burnAddress, token, BigInteger.valueOf(100));
      String txId = jsonRespBO.getTxId();
      TransferQueryRespBO txDetail = blockchainService.getTransferDetails(txId);
      if (txDetail != null) {
        token.setBlockHeight(txDetail.getBlockHeight());
        token.setUpdatedBy(username);
        token.setUpdatedDate(new Date());
        // update tx_history table instead of sp_token?
        token.setStatus(SPTokenStatus.KNOCK_OUT);
        repository.save(token);
      }

    } catch (Exception e) {
      LOGGER.error("Exception in transferToBurnAddress(). ", e);
      throw new ServerErrorException("Exception in transferToBurnAddress(). ", e);
    }
  }

  public void checkSPTokenMaturity() {
    List<SPToken> tokens = repository.fetchAllActiveTokens(SPTokenStatus.ACTIVE);
    for (SPToken token: tokens) {
      Date tokenMaturityDate = token.getMaturityDate();
      Date currentDate = new Date();
      if (tokenMaturityDate.before(currentDate)) {
        token.setStatus(SPTokenStatus.CONTRACT_MATURITY);
        System.out.println("Contract maturity reached for token: " + token.getTokenCode());
        repository.save(token);
      }
    }
  }

}
