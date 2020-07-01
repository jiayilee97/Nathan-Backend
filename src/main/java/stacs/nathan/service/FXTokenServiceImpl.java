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
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.FXTokenDataEntryRequestDto;
import stacs.nathan.dto.request.FXTokenRequestDto;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.response.ClientOpenPositionResponseDto;
import stacs.nathan.dto.response.FXTokenDataEntryResponseDto;
import stacs.nathan.dto.response.FXTokenResponseDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.FXToken;
import stacs.nathan.entity.FXTokenDataEntry;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;
import stacs.nathan.repository.FXTokenDataEntryRepository;
import stacs.nathan.repository.FXTokenRepository;
import stacs.nathan.repository.SPTokenRepository;
import stacs.nathan.utils.enums.FXTokenStatus;
import stacs.nathan.utils.enums.SPTokenStatus;
import stacs.nathan.utils.enums.TokenType;
import java.math.BigInteger;
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
  UserService userService;

  @Autowired
  BlockchainService blockchainService;

  @Value("${stacs.burn.address}")
  String burnAddress;

  public List<SPTokenResponseDto> fetchAvailableTokens(User user) {
    List<SPTokenResponseDto> response = spTokenRepository.fetchAvailableTokens(user);
    response.removeIf(obj -> obj.getAvailability() == false);
    return response;
  }

  public void createFXToken(FXTokenRequestDto dto) throws ServerErrorException {
    LOGGER.debug("Entering createFXToken().");
    try{
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      JsonRespBO jsonRespBO = blockchainService.createToken(loggedInUser, TokenType.FX_TOKEN, dto.getAmount());

      JsonParser parser = new JsonParser();
      JsonObject txResponse = (JsonObject) parser.parse(jsonRespBO.getTxId());
      String txId = txResponse.get("txId").getAsString();
      FXToken token = convertToFXToken(dto);
      token.setCtxId(txId);
      token.setIssuerId(dto.getIssuerId());
      token.setIssuerAddress(loggedInUser.getWalletAddress());
      token.setCreatedBy(username);
      token.setStatus(FXTokenStatus.UNCONFIRMED_IN_CHAIN); // add new status "unconfirmed in chain"
      TokenQueryRespBO txDetail = blockchainService.getTxDetails(txId);
      if (txDetail != null) {
        token.setBlockHeight(txDetail.getBlockHeight());
        token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
        token.setStatus(FXTokenStatus.OPEN);
        fxTokenRepository.save(token);
      }
    } catch (Exception e){
      LOGGER.error("Exception in createFXToken().", e);
      throw new ServerErrorException("Exception in createFXToken().", e);
    }
  }

  public FXToken convertToFXToken(FXTokenRequestDto dto) throws ServerErrorException {
    FXToken token = new FXToken();
    SPToken availableToken = spTokenRepository.findAvailableSPTokenByTokenCode(dto.getSpTokenCode());
    if (availableToken.getFxToken() == null) {
      spTokenRepository.updateSPTokenAvailabilityByTokenCode(dto.getSpTokenCode());
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
      if (spToken.getStatus() != SPTokenStatus.BURNT) {
        LOGGER.error("SP Token not closed.");
        throw new ServerErrorException("SP Token not closed.");
      }
      JsonRespBO jsonRespBO = blockchainService.transferToken(loggedInUser, burnAddress, fxToken, new BigInteger(String.valueOf(1)));
      String txId = jsonRespBO.getTxId();
      TransferQueryRespBO txDetail = blockchainService.getTransferDetails(txId);
      if (txDetail != null) {
        fxToken.setBlockHeight(txDetail.getBlockHeight());
        fxToken.setUpdatedBy(username);
        fxToken.setUpdatedDate(new Date());
        fxToken.setStatus(FXTokenStatus.CLOSED);
        fxTokenRepository.save(fxToken);
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
      if (dto.getPrice().compareTo(spToken.getKnockOutPrice()) >= 0) {
        spToken.setStatus(SPTokenStatus.KNOCK_OUT);
        spTokenRepository.save(spToken);
      }
        data.setCreatedBy(username);
        // TODO: Check price and trigger smart contract?
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

  public void execute() {
    List<FXToken> tokens = fxTokenRepository.fetchAllUnconfirmedChain(FXTokenStatus.UNCONFIRMED_IN_CHAIN);
    for (FXToken token: tokens) {
      TokenQueryRespBO txDetail = blockchainService.getTxDetails(token.getCtxId());
      if (txDetail != null) {
        token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
        token.setBlockHeight(txDetail.getBlockHeight());
        token.setStatus(FXTokenStatus.OPEN);
        fxTokenRepository.save(token);
      }
      blockchainService.getTxDetails(token.getCtxId());
    }
  }

}
