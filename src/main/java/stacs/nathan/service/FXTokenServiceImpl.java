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
import stacs.nathan.dto.request.FXTokenDataEntryRequestDto;
import stacs.nathan.dto.request.FXTokenRequestDto;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.response.ClientOpenPositionResponseDto;
import stacs.nathan.dto.response.FXTokenDataEntryResponseDto;
import stacs.nathan.dto.response.FXTokenResponseDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.*;
import stacs.nathan.repository.BalanceRepository;
import stacs.nathan.repository.FXTokenDataEntryRepository;
import stacs.nathan.repository.FXTokenRepository;
import stacs.nathan.repository.SPTokenRepository;
import stacs.nathan.utils.enums.FXTokenStatus;
import stacs.nathan.utils.enums.SPTokenStatus;
import stacs.nathan.utils.enums.TokenType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FXTokenServiceImpl implements FXTokenService {
  private static final Logger LOGGER = LoggerFactory.getLogger(FXTokenServiceImpl.class);

  @Autowired
  FXTokenRepository fxTokenRepository;

  @Autowired
  SPTokenRepository spTokenRepository;

  @Autowired
  FXTokenDataEntryRepository fxTokenDataEntryRepository;

  @Autowired
  BalanceRepository balanceRepository;

  @Autowired
  UserService userService;

  @Autowired
  BlockchainService blockchainService;

  @Autowired
  BalanceService balanceService;

  @Autowired
  SPTokenService spTokenService;

  @Value("${stacs.burn.address}")
  String burnAddress;

  @Value("${stacs.app.address}")
  String appWalletAddress;

  public List<SPTokenResponseDto> fetchAvailableTokens(User user) {
    List<SPToken> response = spTokenRepository.fetchAllActiveTokens(SPTokenStatus.ACTIVE);
    response.removeIf(obj -> obj.getFxToken() != null);
    List<SPTokenResponseDto> responseDtoList = new ArrayList<>();
    for (SPToken spToken : response) {
      responseDtoList.add(new SPTokenResponseDto(spToken));
    }
    return responseDtoList;
  }

  public String fetchAppWalletAddress() {
    return appWalletAddress;
  }

  @Transactional(rollbackFor = ServerErrorException.class)
  public void createFXToken(FXTokenRequestDto dto) throws ServerErrorException {
    LOGGER.debug("Entering createFXToken().");
    try{
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      SPToken spToken = spTokenRepository.findAvailableSPTokenByTokenCode(dto.getSpTokenCode());
      FXToken token = convertToFXToken(dto);
      token.setIssuerId(loggedInUser.getUuid());
      token.setIssuerAddress(loggedInUser.getWalletAddress());
      token.setCreatedBy(username);
      BigDecimal tokenAmount = spToken.getNotionalAmount();
      JsonRespBO jsonRespBO = blockchainService.createToken(loggedInUser, TokenType.FX_TOKEN, tokenAmount);
      if (jsonRespBO == null) {
        token.setStatus(FXTokenStatus.CHAIN_UNAVAILABLE);
        fxTokenRepository.save(token);
      } else {
        processAvailableChain(token, jsonRespBO);
      }
    } catch (Exception e){
      LOGGER.error("Exception in createFXToken().", e);
      throw new ServerErrorException("Exception in createFXToken().", e);
    }
  }

  void processAvailableChain(FXToken token, JsonRespBO jsonRespBO){
    JsonParser parser = new JsonParser();
    JsonObject txResponse = (JsonObject) parser.parse(jsonRespBO.getTxId());
    String txId = txResponse.get("txId").getAsString();
    token.setCtxId(txId);
    token.setStatus(FXTokenStatus.UNCONFIRMED_IN_CHAIN);
    fxTokenRepository.save(token);
    TokenQueryRespBO txDetail = blockchainService.getTxDetails(txId);
    if (txDetail != null) {
      token.setBlockHeight(txDetail.getBlockHeight());
      token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
      token.setStatus(FXTokenStatus.OPEN);
      fxTokenRepository.save(token);
      SPToken spToken = spTokenRepository.findAvailableSPTokenByTokenCode(token.getSpToken().getTokenCode());
      spToken.setFxToken(token);
      spTokenRepository.save(spToken);
      Balance balance = new Balance();
      balance.setUser(spToken.getUser());
      balance.setTokenType(TokenType.FX_TOKEN);
      balance.setTokenCode(token.getTokenCode());
      balance.setBalanceAmount(token.getAmount());
      balanceService.createBalance(balance);
    }
  }

  public FXToken convertToFXToken(FXTokenRequestDto dto) throws ServerErrorException {
    FXToken token = new FXToken();
    SPToken availableToken = spTokenRepository.findAvailableSPTokenByTokenCode(dto.getSpTokenCode());
    if (availableToken.getFxToken() == null) {
      //spTokenRepository.updateSPTokenAvailabilityByTokenCode(dto.getSpTokenCode());
      token.setSpToken(availableToken);
      token.setCurrencyCode(dto.getCurrencyCode());
      token.setAmount(dto.getAmount());
      token.setFxCurrency(dto.getFxCurrency());
      token.setTokenCode(dto.getTokenCode());
      return token;
    } else {
      LOGGER.error("SP Token selected is not available");
      throw new ServerErrorException("SP Token not available");
    }
  }

  public void closeFXToken(String tokenCode) throws ServerErrorException {
    LOGGER.debug("Entering closeFXToken().");
    try {
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      FXToken fxToken = fxTokenRepository.findByTokenCode(tokenCode);
      SPToken spToken = fxToken.getSpToken();
      Balance fxBalance = balanceService.fetchBalanceByTokenCodeAndId(fxToken.getTokenCode(), loggedInUser.getId());
//      if (spToken.getStatus() != SPTokenStatus.KNOCK_OUT) {
//        LOGGER.error("SP Token not closed.");
//        throw new ServerErrorException("SP Token not closed.");
//      }
      JsonRespBO jsonRespBO = blockchainService.transferToken(loggedInUser, loggedInUser.getWalletAddress(), burnAddress, fxToken, fxBalance.getBalanceAmount().toBigInteger());
      String txId = jsonRespBO.getTxId();
      TransferQueryRespBO txDetail = blockchainService.getTransferDetails(txId);
      if (txDetail != null) {
        fxToken.setUpdatedBy(username);
        fxToken.setUpdatedDate(new Date());
        fxToken.setStatus(FXTokenStatus.CLOSED);
        fxTokenRepository.save(fxToken);

        fxBalance.setBalanceAmount(BigDecimal.valueOf(0));
        balanceRepository.save(fxBalance);
      }
    } catch (Exception e) {
      LOGGER.error("Exception in closeFXToken().", e);
      throw new ServerErrorException("Exception in closeFXToken().", e);
    }
  }

  public void enterSpotPrice(FXTokenDataEntryRequestDto dto) throws ServerErrorException {
    String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    try {
      FXTokenDataEntry data = convertToFXTokenDataEntry(dto);
      SPToken spToken = data.getFxToken().getSpToken();
      FXToken fxToken = data.getFxToken();
      if (dto.getPrice().compareTo(spToken.getKnockOutPrice()) >= 0) {
        spToken.setStatus(SPTokenStatus.KNOCK_OUT);
        spToken.setUpdatedBy(username);
        spTokenRepository.save(spToken);
        spTokenService.transferToBurnAddress(spToken.getTokenCode());

        // Update fx token status
        fxToken.setStatus(FXTokenStatus.KNOCK_OUT);
        fxTokenRepository.save(fxToken);
      }
        data.setCreatedBy(username);
        // TODO: Transfer FX Tokens if below knockout price
        fxTokenDataEntryRepository.save(data);
    } catch (Exception e) {
      LOGGER.error("Exception in enterSpotPrice().", e);
      throw new ServerErrorException("Exception in enterSpotPrice().", e);
    }
  }

  public FXTokenDataEntry convertToFXTokenDataEntry(FXTokenDataEntryRequestDto dto) throws ServerErrorException {
    FXTokenDataEntry data = new FXTokenDataEntry();
    FXToken fxToken = fxTokenRepository.findByTokenCode(dto.getFxTokenCode());
    if (fxToken.getStatus() != FXTokenStatus.CLOSED) {
      data.setEntryDate(dto.getEntryDate());
      data.setFxCurrency(dto.getFxCurrency());
      data.setFxToken(fxToken);
      data.setPrice(dto.getPrice());
      return data;
    } else {
      throw new ServerErrorException("FX Token not available");
    }
  }

  public List<ClientOpenPositionResponseDto> fetchClientOpenPosition(String issuerId){
    return fxTokenRepository.fetchClientOpenPosition(issuerId);
  }

  public List<FXTokenResponseDto> fetchAllFxTokens(User user) {
    return fxTokenRepository.fetchAllFxTokens();
  }

  public FXTokenResponseDto fetchTokenById(String tokenCode) { return fxTokenRepository.fetchTokenById(tokenCode); }

  public List<FXTokenResponseDto> fetchAvailableFXTokens() {
    return fxTokenRepository.fetchAvailableFXTokens();
  }

  public List<FXTokenDataEntryResponseDto> fetchDataEntryHistory() {
    return fxTokenDataEntryRepository.fetchAll();
  }

  public FXToken fetchByTokenCode(String tokenCode) { return fxTokenRepository.findByTokenCode(tokenCode); }

  public void executeUnconfirmedChain() {
    LOGGER.debug("Entering executeUnconfirmedChain().");
    try {
      List<FXToken> tokens = fxTokenRepository.findByStatus(FXTokenStatus.UNCONFIRMED_IN_CHAIN);
      for (FXToken token: tokens) {
        TokenQueryRespBO txDetail = blockchainService.getTxDetails(token.getCtxId());
        if (txDetail != null) {
          token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
          token.setBlockHeight(txDetail.getBlockHeight());
          token.setStatus(FXTokenStatus.OPEN);
          fxTokenRepository.save(token);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Exception in executeUnconfirmedChain().", e);
    }
  }

  public void executeUnavailableChain(){
    LOGGER.debug("Entering executeUnavailableChain().");
    try {
      List<FXToken> tokens = fxTokenRepository.findByStatus(FXTokenStatus.CHAIN_UNAVAILABLE);
      for (FXToken token : tokens) {
        JsonRespBO jsonRespBO = blockchainService.createToken(token.getSpToken().getUser(), TokenType.FX_TOKEN, token.getAmount());
        if (jsonRespBO != null) {
          processAvailableChain(token, jsonRespBO);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Exception in executeUnavailableChain().", e);
    }
  }

  public List<FXTokenResponseDto> fetchMaturedOrKnockout() {
    return fxTokenRepository.fetchAllMaturedOrKnockout();
  }
}
