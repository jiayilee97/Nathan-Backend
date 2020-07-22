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
import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.FXTokenDataEntryRequestDto;
import stacs.nathan.dto.request.FXTokenRequestDto;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.response.*;
import stacs.nathan.entity.*;
import stacs.nathan.repository.*;
import stacs.nathan.utils.enums.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FXTokenServiceImpl implements FXTokenService {
  private static final Logger LOGGER = LoggerFactory.getLogger(FXTokenServiceImpl.class);

  @Autowired
  FXTokenRepository repository;

  @Autowired
  FXTokenDataEntryRepository fxTokenDataEntryRepository;

  @Autowired
  TradeHistoryService tradeHistoryService;

  @Autowired
  TransactionHistoryService transactionHistoryService;

  @Autowired
  UserService userService;

  @Autowired
  BlockchainService blockchainService;

  @Autowired
  BalanceService balanceService;

  @Autowired
  SPTokenService spTokenService;

  @Autowired
  CodeValueService codeValueService;

  @Value("${stacs.burn.address}")
  String burnAddress;

  @Value("${stacs.app.address}")
  String appWalletAddress;

  public CreateFXTokenInitDto fetchInitForm(){
    CreateFXTokenInitDto dto = new CreateFXTokenInitDto();
    dto.setAppWalletAddress(appWalletAddress);
    dto.setAvailableSPToken(fetchAvailableTokens(userService.fetchLoginUser()));
    dto.setCurrency(codeValueService.findByType(CodeType.CURRENCY));
    return dto;
  }

  public List<SPTokenResponseDto> fetchAvailableTokens(User user) {
    List<SPToken> response = spTokenService.fetchTokensByStatus(SPTokenStatus.ACTIVE);
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

  public void save(FXToken fxToken) {
    repository.save(fxToken);
  }

  @Transactional(rollbackFor = ServerErrorException.class)
  public void createFXToken(FXTokenRequestDto dto) throws ServerErrorException, BadRequestException {
    LOGGER.debug("Entering createFXToken().");
    FXToken token = fetchByTokenCode(dto.getTokenCode());
    if(token != null){
      throw new BadRequestException("Token Code already exists.");
    }
    try{
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      User appWallet = userService.fetchAppAddress();
      SPToken spToken = spTokenService.findAvailableSPTokenByTokenCode(dto.getSpTokenCode());
      token = convertToFXToken(dto);
      token.setIssuerId(appWallet.getUuid());
      token.setIssuerAddress(appWallet.getWalletAddress());
      BigDecimal tokenAmount = spToken.getNotionalAmount();
      token.setStatus(FXTokenStatus.CHAIN_UNAVAILABLE);
      repository.save(token);
      JsonRespBO jsonRespBO = blockchainService.createToken(loggedInUser, appWallet.getWalletAddress(), TokenType.FX_TOKEN, tokenAmount);
      if (jsonRespBO != null) {
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
    User appWallet = userService.fetchAppAddress();
    String txId = txResponse.get("txId").getAsString();
    token.setCtxId(txId);
    token.setStatus(FXTokenStatus.UNCONFIRMED_IN_CHAIN);
    repository.save(token);
    TokenQueryRespBO txDetail = blockchainService.getTxDetails(txId);
    if (txDetail != null) {
      token.setBlockHeight(txDetail.getBlockHeight());
      token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
      token.setStatus(FXTokenStatus.OPEN);
      repository.save(token);
      SPToken spToken = spTokenService.findAvailableSPTokenByTokenCode(token.getSpToken().getTokenCode());
      spToken.setFxToken(token);
      spTokenService.save(spToken);
      Balance balance = new Balance();
      balance.setUser(appWallet);
      balance.setTokenType(TokenType.FX_TOKEN);
      balance.setTokenCode(token.getTokenCode());
      balance.setBalanceAmount(token.getAmount());
      balanceService.createBalance(balance);
    }
  }

  public FXToken convertToFXToken(FXTokenRequestDto dto) throws ServerErrorException {
    FXToken token = new FXToken();
    SPToken availableToken = spTokenService.findAvailableSPTokenByTokenCode(dto.getSpTokenCode());
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
      FXToken fxToken = repository.findByTokenCode(tokenCode);
      SPToken spToken = fxToken.getSpToken();
      User appWallet = userService.fetchAppAddress();
      User investor = userService.fetchUserByClientId(spToken.getClientId());
      Balance appWalletFxBalance = balanceService.fetchBalanceByTokenCodeAndId(fxToken.getTokenCode(), appWallet.getId());
      Balance investorWalletFxBalance = balanceService.fetchBalanceByTokenCodeAndId(fxToken.getTokenCode(), investor.getId());
//      if (spToken.getStatus() != SPTokenStatus.KNOCK_OUT) {
//        LOGGER.error("SP Token not closed.");
//        throw new ServerErrorException("SP Token not closed.");
//      }
      JsonRespBO appWalletJsonRespBO = blockchainService.transferToken(loggedInUser, appWallet.getWalletAddress(), burnAddress, fxToken, appWalletFxBalance.getBalanceAmount().toBigInteger());
      BigDecimal tokenAmountInAppWallet = appWalletFxBalance.getBalanceAmount();
      String appWalletTxId = appWalletJsonRespBO.getTxId();
      TransferQueryRespBO appWalletTxDetail = blockchainService.getTransferDetails(appWalletTxId);
      if (appWalletTxDetail != null) {
        fxToken.setStatus(FXTokenStatus.CLOSED);
        repository.save(fxToken);
        appWalletFxBalance.setBalanceAmount(BigDecimal.valueOf(0));
        balanceService.createBalance(appWalletFxBalance);

        TransactionHistory tx = new TransactionHistory();
        tx.setTokenContractAddress(fxToken.getTokenContractAddress());
        tx.setAmount(tokenAmountInAppWallet);
        tx.setFromAddress(appWallet.getWalletAddress());
        tx.setToAddress(burnAddress);
        tx.setBlockHeight(appWalletTxDetail.getBlockHeight());
        tx.setStatus(TransactionStatus.KNOCK_OUT);
        tx.setCtxId(appWalletTxId);
        tx.setTokenType(TokenType.FX_TOKEN);
        tx.setTokenCode(fxToken.getTokenCode());
        tx.setTokenId(fxToken.getId());
        transactionHistoryService.save(tx);
      }

      BigDecimal tokenAmountInInvestorWallet = investorWalletFxBalance.getBalanceAmount();
      JsonRespBO investorWalletJsonRespBO = blockchainService.transferToken(loggedInUser, investor.getWalletAddress(), burnAddress, fxToken, tokenAmountInInvestorWallet.toBigInteger());
      String investorWalletTxId = investorWalletJsonRespBO.getTxId();
      TransferQueryRespBO investorWalletTxDetail = blockchainService.getTransferDetails(appWalletTxId);
      if (investorWalletTxDetail != null) {
        fxToken.setUpdatedDate(new Date());
        fxToken.setStatus(FXTokenStatus.CLOSED);
        repository.save(fxToken);

        investorWalletFxBalance.setBalanceAmount(BigDecimal.valueOf(0));
        balanceService.createBalance(investorWalletFxBalance);

        TransactionHistory tx = new TransactionHistory();
        tx.setTokenContractAddress(fxToken.getTokenContractAddress());
        tx.setAmount(tokenAmountInInvestorWallet);
        tx.setFromAddress(investor.getWalletAddress());
        tx.setToAddress(burnAddress);
        tx.setBlockHeight(investorWalletTxDetail.getBlockHeight());
        tx.setStatus(TransactionStatus.KNOCK_OUT);
        tx.setCtxId(investorWalletTxId);
        tx.setTokenType(TokenType.FX_TOKEN);
        tx.setTokenCode(fxToken.getTokenCode());
        tx.setTokenId(fxToken.getId());
        transactionHistoryService.save(tx);
      }
    } catch (Exception e) {
      LOGGER.error("Exception in closeFXToken().", e);
      throw new ServerErrorException("Exception in closeFXToken().", e);
    }
  }

  public void enterSpotPrice(FXTokenDataEntryRequestDto dto) throws ServerErrorException {
    String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    User loggedInUser = userService.fetchByUsername(username);
    User appWallet = userService.fetchAppAddress();
    try {
      FXTokenDataEntry data = convertToFXTokenDataEntry(dto);
      SPToken spToken = data.getFxToken().getSpToken();
      FXToken fxToken = data.getFxToken();
      User client = userService.fetchUserByClientId(spToken.getClientId());

      // Set status of FX and SP Token to knockout if spot price is greater than knockout price
      if (dto.getPrice().compareTo(spToken.getKnockOutPrice()) >= 0) {
        spToken.setStatus(SPTokenStatus.KNOCK_OUT);
        spTokenService.save(spToken);
        spTokenService.transferToBurnAddress(spToken.getTokenCode());

        // Update fx token status
        fxToken.setStatus(FXTokenStatus.KNOCK_OUT);
        repository.save(fxToken);
      }

      // Auto transfer FX Tokens from app wallet address to investor wallet
      else {
        Balance fxTokenBalance = balanceService.fetchBalanceByTokenCodeAndId(fxToken.getTokenCode(), appWallet.getId());
        BigDecimal remainingAmount = fxTokenBalance.getBalanceAmount().subtract(spToken.getFixingAmount());
        fxTokenBalance.setBalanceAmount(remainingAmount);
        balanceService.createBalance(fxTokenBalance);

        // Save balance for receiver wallet
        Balance receiverBalance = balanceService.fetchBalanceByTokenCodeAndId(fxToken.getTokenCode(), client.getId());
        if (receiverBalance == null) {
          Balance newBalance = new Balance();
          newBalance.setBalanceAmount(spToken.getFixingAmount());
          newBalance.setTokenCode(fxToken.getTokenCode());
          newBalance.setTokenType(TokenType.FX_TOKEN);
          newBalance.setUser(client);
          balanceService.createBalance(newBalance);
        } else {
          BigDecimal newAmount = receiverBalance.getBalanceAmount().add(spToken.getFixingAmount());
          receiverBalance.setBalanceAmount(newAmount);
          balanceService.createBalance(receiverBalance);
        }

        // Update trade history
        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.setSide("BUY");
        tradeHistory.setQuantity(spToken.getFixingAmount());
        tradeHistory.setTokenId(fxToken.getId());
        tradeHistory.setUnderlying(fxToken.getFxCurrency());
        tradeHistory.setTokenType(TokenType.FX_TOKEN);
        tradeHistory.setSpToken(fxToken.getSpToken());
        tradeHistory.setUser(client);
        tradeHistoryService.save(tradeHistory);

        if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
          throw new BadRequestException("Insufficient balance for transfer");
        } else {
          JsonRespBO jsonRespBO = blockchainService.transferToken(loggedInUser, appWallet.getWalletAddress(), client.getWalletAddress(), fxToken, spToken.getFixingAmount().toBigInteger());
          String txId = jsonRespBO.getTxId();
          TransferQueryRespBO txDetail = blockchainService.getTransferDetails(txId);
          if (txDetail != null) {
            // Update Transaction history
            TransactionHistory tx = new TransactionHistory();
            tx.setTokenContractAddress(fxToken.getTokenContractAddress());
            tx.setAmount(spToken.getFixingAmount());
            tx.setFromAddress(appWallet.getWalletAddress());
            tx.setToAddress(client.getWalletAddress());
            tx.setBlockHeight(txDetail.getBlockHeight());
            tx.setStatus(TransactionStatus.DEPOSIT);
            tx.setCtxId(txId);
            tx.setTokenCode(fxToken.getTokenCode());
            tx.setTokenType(TokenType.FX_TOKEN);
            tx.setTokenId(fxToken.getId());
            transactionHistoryService.save(tx);
          }
        }
      }
        // TODO: Transfer FX Tokens if below knockout price
        fxTokenDataEntryRepository.save(data);
    } catch (Exception e) {
      LOGGER.error("Exception in enterSpotPrice().", e);
      throw new ServerErrorException("Exception in enterSpotPrice().", e);
    }
  }

  public FXTokenDataEntry convertToFXTokenDataEntry(FXTokenDataEntryRequestDto dto) throws ServerErrorException {
    FXTokenDataEntry data = new FXTokenDataEntry();
    FXToken fxToken = repository.findByTokenCode(dto.getFxTokenCode());
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

  public List<ClientOpenPositionResponseDto> fetchClientOpenPosition(String clientId){
    User user = userService.fetchById(Long.parseLong(clientId));
    return repository.fetchClientOpenPosition(user.getClientId());
  }

  public List<FXTokenResponseDto> fetchAllFxTokens(User user) {
    return repository.fetchAllFxTokens();
  }

  public FXTokenResponseDto fetchTokenById(String tokenCode) { return repository.fetchTokenById(tokenCode); }

  public List<FXTokenResponseDto> fetchAvailableFXTokens() {
    return repository.fetchAvailableFXTokens();
  }

  public List<FXTokenDataEntryResponseDto> fetchDataEntryHistory() {
    return fxTokenDataEntryRepository.fetchAll();
  }

  public FXToken fetchByTokenCode(String tokenCode) { return repository.findByTokenCode(tokenCode); }

  public void executeUnconfirmedChain() {
    LOGGER.debug("Entering executeUnconfirmedChain().");
    try {
      List<FXToken> tokens = repository.findByStatus(FXTokenStatus.UNCONFIRMED_IN_CHAIN);
      for (FXToken token: tokens) {
        TokenQueryRespBO txDetail = blockchainService.getTxDetails(token.getCtxId());
        if (txDetail != null) {
          User appWallet = userService.fetchAppAddress();
          token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
          token.setBlockHeight(txDetail.getBlockHeight());
          token.setStatus(FXTokenStatus.OPEN);
          repository.save(token);
          SPToken spToken = spTokenService.findAvailableSPTokenByTokenCode(token.getSpToken().getTokenCode());
          spToken.setFxToken(token);
          spTokenService.save(spToken);
          Balance balance = new Balance();
          balance.setUser(appWallet);
          balance.setTokenType(TokenType.FX_TOKEN);
          balance.setTokenCode(token.getTokenCode());
          balance.setBalanceAmount(token.getAmount());
          balanceService.createBalance(balance);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Exception in executeUnconfirmedChain().", e);
    }
  }

  public void executeUnavailableChain(){
    LOGGER.debug("Entering executeUnavailableChain().");
    try {
      List<FXToken> tokens = repository.findByStatus(FXTokenStatus.CHAIN_UNAVAILABLE);
      for (FXToken token : tokens) {
        JsonRespBO jsonRespBO = blockchainService.createToken(token.getSpToken().getUser(), appWalletAddress, TokenType.FX_TOKEN, token.getAmount());
        if (jsonRespBO != null) {
          processAvailableChain(token, jsonRespBO);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Exception in executeUnavailableChain().", e);
    }
  }

  public List<FXTokenResponseDto> fetchMaturedOrKnockout() {
    return repository.fetchAllMaturedOrKnockout();
  }
}
