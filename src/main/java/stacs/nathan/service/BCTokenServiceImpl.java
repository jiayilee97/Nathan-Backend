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
import stacs.nathan.core.audit.action.AudibleActionImplementation;
import stacs.nathan.core.audit.action.annotation.AudibleActionTrail;
import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.request.TransferBCTokenRequestDto;
import stacs.nathan.dto.request.TransferBCTokenToOpsRequestDto;
import stacs.nathan.dto.response.BCTokenResponseDto;
import stacs.nathan.dto.response.CreateBCTokenInitDto;
import stacs.nathan.entity.*;
import stacs.nathan.repository.BCTokenRepository;
import stacs.nathan.repository.TradeHistoryRepository;
import stacs.nathan.utils.constancs.AuditActionConstants;
import stacs.nathan.utils.enums.BCTokenStatus;
import stacs.nathan.utils.enums.CodeType;
import stacs.nathan.utils.enums.TokenType;
import stacs.nathan.utils.enums.TransactionStatus;
import java.math.BigDecimal;
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

  @Autowired
  CodeValueService codeValueService;

  @Autowired
  BalanceService balanceService;

  @Autowired
  TransactionHistoryService transactionHistoryService;

  @Autowired
  FXTokenService fxTokenService;

  @Autowired
  TradeHistoryRepository tradeHistoryRepository;

  @Value("${stacs.app.address}")
  String appAddress;

  public CreateBCTokenInitDto fetchInitForm(){
    String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    User loggedInUser = userService.fetchByUsername(username);
    CreateBCTokenInitDto dto = new CreateBCTokenInitDto();
    dto.setUnderlying(codeValueService.findByType(CodeType.CURRENCY));
    dto.setIssuingAddress(loggedInUser.getWalletAddress());
    return dto;
  }

  @Transactional(rollbackFor = ServerErrorException.class)
  @AudibleActionTrail(module = AuditActionConstants.BC_TOKEN_MODULE, action = AuditActionConstants.CREATE_BC_TOKEN)
  public AudibleActionImplementation<BaseCurrencyToken> createBCToken(BCTokenRequestDto dto) throws ServerErrorException, BadRequestException {
    LOGGER.debug("Entering createBCToken().");
    BaseCurrencyToken token = repository.findByTokenCode(dto.getTokenCode());
    if(token != null){
      throw new BadRequestException("Token Code already exists.");
    }
    try{
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      token = convertToBCToken(dto);
      token.setUser(loggedInUser);
      token.setIssuerId(loggedInUser.getUuid());
      token.setIssuerAddress(loggedInUser.getWalletAddress());
      token.setStatus(BCTokenStatus.CHAIN_UNAVAILABLE);
      repository.save(token);
      JsonRespBO jsonRespBO = blockchainService.createToken(loggedInUser, loggedInUser.getWalletAddress(), TokenType.BC_TOKEN, dto.getAmount());
      if (jsonRespBO != null) {
        processAvailableChain(token, jsonRespBO);
      }
      return new AudibleActionImplementation<>(token);
    } catch (Exception e) {
      LOGGER.error("Exception in createBCToken().", e);
      throw new ServerErrorException("Exception in createBCToken().", e);
    }
  }

  public void processAvailableChain(BaseCurrencyToken token, JsonRespBO jsonRespBO) {
    JsonParser parser = new JsonParser();
    JsonObject txResponse = (JsonObject) parser.parse(jsonRespBO.getTxId());
    String txId = txResponse.get("txId").getAsString();
    token.setCtxId(txId);
    token.setStatus(BCTokenStatus.UNCONFIRMED_IN_CHAIN);
    repository.save(token);
    TokenQueryRespBO txDetail = blockchainService.getTxDetails(txId);
    if(txDetail != null) {
      token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
      token.setBlockHeight(txDetail.getBlockHeight());
      token.setStatus(BCTokenStatus.OPEN);
      repository.save(token);
      Balance balance = new Balance();
      balance.setUser(token.getUser());
      balance.setTokenType(TokenType.BC_TOKEN);
      balance.setTokenCode(token.getTokenCode());
      balance.setBalanceAmount(token.getAmount());
      balanceService.createBalance(balance);
    }
  }

//  public List<BCTokenResponseDto> fetchBalanceByIssuerAddress(String issuerAddress) throws ServerErrorException {
//    LOGGER.debug("Entering fetchBalanceByIssuerAddress().");
//    try {
//      return repository.fetchAllByIssuerAddress(issuerAddress, BCTokenStatus.OPEN);
//    } catch (Exception e) {
//      LOGGER.error("Exception in fetchBalanceByIssuerAddress().", e);
//      throw new ServerErrorException("Exception in fetchBalanceByIssuerAddress().", e);
//    }
//  }

  public List<BCTokenResponseDto> fetchAllByIssuerAddress(String issuerAddress) throws ServerErrorException {
    LOGGER.debug("Entering fetchAllBCTokens().");
    try {
      List<BCTokenResponseDto> tokenList = repository.fetchAllByIssuerAddress(issuerAddress, BCTokenStatus.OPEN);
      for (BCTokenResponseDto token : tokenList) {
        User issuer = userService.fetchByWalletAddress(issuerAddress);
        Balance tokenBalance = balanceService.fetchBalanceByTokenCodeAndId(token.getTokenCode(), issuer.getId());
        token.setBalance(tokenBalance.getBalanceAmount());
      }
      return tokenList;
    } catch (Exception e) {
      LOGGER.error("Exception in fetchAllBCTokens().", e);
      throw new ServerErrorException("Exception in fetchAllBCTokens().", e);
    }
  }

  public BCTokenResponseDto fetchTokenByTokenCode(String tokenCode) throws ServerErrorException {
    LOGGER.debug("Entering fetchTokenById().");
    try {
      return repository.fetchByTokenCode(tokenCode);
    } catch (Exception e) {
      LOGGER.error("Exception in fetchTokenById().", e);
      throw new ServerErrorException("Exception in fetchTokenById().", e);
    }
  }

  public void executeUnconfirmedChain() {
    LOGGER.debug("Entering executeUnconfirmedChain().");
    try {
      List<BaseCurrencyToken> tokens = repository.findByStatus(BCTokenStatus.UNCONFIRMED_IN_CHAIN);
      for (BaseCurrencyToken token : tokens) {
        TokenQueryRespBO txDetail = blockchainService.getTxDetails(token.getCtxId());
        if (txDetail != null) {
          token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
          token.setBlockHeight(txDetail.getBlockHeight());
          token.setStatus(BCTokenStatus.OPEN);
          repository.save(token);
          Balance balance = new Balance();
          balance.setUser(token.getUser());
          balance.setTokenType(TokenType.BC_TOKEN);
          balance.setTokenCode(token.getTokenCode());
          balance.setBalanceAmount(token.getAmount());
          balanceService.createBalance(balance);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Exception in executeUnconfirmedChain().", e);
    }
  }

  public void executeUnavailableChain() {
    LOGGER.debug("Entering executeUnavailableChain().");
    try {
      List<BaseCurrencyToken> tokens = repository.findByStatus(BCTokenStatus.CHAIN_UNAVAILABLE);
      for (BaseCurrencyToken token : tokens) {
        JsonRespBO jsonRespBO = blockchainService.createToken(token.getUser(), token.getIssuerAddress(), TokenType.BC_TOKEN, token.getAmount());
        if (jsonRespBO != null) {
          processAvailableChain(token, jsonRespBO);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Exception in executeUnavailableChain().", e);
    }
  }

  public BaseCurrencyToken convertToBCToken(BCTokenRequestDto dto) {
    BaseCurrencyToken token = new BaseCurrencyToken();
    token.setUnderlyingCurrency(dto.getUnderlyingCurrency());
    token.setTokenCode(dto.getTokenCode());
    token.setCurrencyCode(dto.getCurrencyCode());
    token.setAmount(dto.getAmount());
    return token;
  }

  @Transactional(rollbackFor = ServerErrorException.class)
  public void transferBCToken(TransferBCTokenRequestDto dto) throws ServerErrorException {
    LOGGER.debug("Entering transferBCToken().");
    try{
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      BaseCurrencyToken bcToken = repository.findByTokenCode(dto.getBcTokenCode());
      User investor = userService.fetchByWalletAddress(dto.getInvestorWalletAddress());
      Balance bcTokenBalance = balanceService.fetchBalanceByTokenCodeAndId(dto.getBcTokenCode(), loggedInUser.getId());
      BigDecimal remainingAmount = bcTokenBalance.getBalanceAmount().subtract(dto.getAmount());
      if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
        throw new BadRequestException("Insufficient balance for transfer");
      } else {
        JsonRespBO jsonRespBO = blockchainService.transferToken(loggedInUser, loggedInUser.getWalletAddress(), investor.getWalletAddress(), bcToken, dto.getAmount().toBigInteger());
        String txId = jsonRespBO.getTxId();
        TransferQueryRespBO txDetail = blockchainService.getTransferDetails(txId);
        if (txDetail != null) {
          // Save balance for sender wallet
          bcTokenBalance.setBalanceAmount(remainingAmount);
          balanceService.createBalance(bcTokenBalance);

          // Save balance for receiver wallet
          Balance receiverBalance = balanceService.fetchBalanceByTokenCodeAndId(dto.getBcTokenCode(), investor.getId());
          if (receiverBalance == null) {
            Balance newBalance = new Balance();
            newBalance.setBalanceAmount(dto.getAmount());
            newBalance.setTokenCode(dto.getBcTokenCode());
            newBalance.setTokenType(TokenType.BC_TOKEN);
            newBalance.setUser(investor);
            balanceService.createBalance(newBalance);
          } else {
            BigDecimal newAmount = receiverBalance.getBalanceAmount().add(dto.getAmount());
            receiverBalance.setBalanceAmount(newAmount);
            balanceService.createBalance(receiverBalance);
          }

          // Update transaction history table
          TransactionHistory tx = new TransactionHistory();
          tx.setTokenContractAddress(bcToken.getTokenContractAddress());
          tx.setAmount(dto.getAmount());
          tx.setFromAddress(loggedInUser.getWalletAddress());
          tx.setToAddress(investor.getWalletAddress());
          tx.setBlockHeight(txDetail.getBlockHeight());
          tx.setStatus(TransactionStatus.DEPOSIT);
          tx.setCtxId(txId);
          tx.setTokenCode(bcToken.getTokenCode());
          tx.setTokenType(TokenType.BC_TOKEN);
          tx.setTokenId(bcToken.getId());
          transactionHistoryService.save(tx);
        }
      }
    } catch (Exception e){
      LOGGER.error("Exception in transferBCToken().", e);
      throw new ServerErrorException("Exception in transferBCToken().", e);
    }
  }

  @Transactional(rollbackFor = ServerErrorException.class)
  public void transferBCTokenToOpsWallet(TransferBCTokenToOpsRequestDto dto) throws ServerErrorException {
    LOGGER.debug("Entering transferBCToken().");
    try{
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      BaseCurrencyToken bcToken = repository.findByTokenCode(dto.getBcTokenCode());
      User sender = userService.fetchByWalletAddress(dto.getSenderAddress());
      User recepient = userService.fetchByWalletAddress(dto.getRecepientAddress());
      Balance bcTokenBalance = balanceService.fetchBalanceByTokenCodeAndId(dto.getBcTokenCode(), sender.getId());
      BigDecimal remainingAmount = bcTokenBalance.getBalanceAmount().subtract(dto.getAmount());
      if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
        throw new BadRequestException("Insufficient balance for transfer");
      } else {
        JsonRespBO jsonRespBO = blockchainService.transferToken(sender, sender.getWalletAddress(), recepient.getWalletAddress(), bcToken, dto.getAmount().toBigInteger());
        String txId = jsonRespBO.getTxId();
        TransferQueryRespBO txDetail = blockchainService.getTransferDetails(txId);
        if (txDetail != null) {
          // Save balance for sender wallet
          bcTokenBalance.setBalanceAmount(remainingAmount);
          balanceService.createBalance(bcTokenBalance);

          // Save balance for receiver wallet
          Balance receiverBalance = balanceService.fetchBalanceByTokenCodeAndId(dto.getBcTokenCode(), recepient.getId());
          if (receiverBalance == null) {
            Balance newBalance = new Balance();
            newBalance.setBalanceAmount(dto.getAmount());
            newBalance.setTokenCode(dto.getBcTokenCode());
            newBalance.setTokenType(TokenType.BC_TOKEN);
            newBalance.setUser(recepient);
            balanceService.createBalance(newBalance);
          } else {
            BigDecimal newAmount = receiverBalance.getBalanceAmount().add(dto.getAmount());
            receiverBalance.setBalanceAmount(newAmount);
            balanceService.createBalance(receiverBalance);
          }

          // Update transaction history table
          TransactionHistory tx = new TransactionHistory();
          tx.setTokenContractAddress(bcToken.getTokenContractAddress());
          tx.setAmount(dto.getAmount());
          tx.setFromAddress(sender.getWalletAddress());
          tx.setToAddress(recepient.getWalletAddress());
          tx.setBlockHeight(txDetail.getBlockHeight());
          tx.setStatus(TransactionStatus.FUND_TRANSFER);
          tx.setCtxId(txId);
          tx.setTokenCode(bcToken.getTokenCode());
          tx.setTokenType(TokenType.BC_TOKEN);
          tx.setTokenId(bcToken.getId());
          transactionHistoryService.save(tx);
        }
      }
    } catch (Exception e){
      LOGGER.error("Exception in transferBCToken().", e);
      throw new ServerErrorException("Exception in transferBCToken().", e);
    }
  }

  @Transactional(rollbackFor = ServerErrorException.class)
  public void opsTrade(TransferBCTokenRequestDto dto) throws ServerErrorException {
    LOGGER.debug("Entering opsTrade().");
    try {
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      BaseCurrencyToken bcToken = repository.findByTokenCode(dto.getBcTokenCode());
      User investor = userService.fetchByWalletAddress(dto.getInvestorWalletAddress());
      Balance bcTokenBalance = balanceService.fetchBalanceByTokenCodeAndId(dto.getBcTokenCode(), loggedInUser.getId());
      if (bcTokenBalance != null) {
        BigDecimal bcRemainingAmount = bcTokenBalance.getBalanceAmount().subtract(dto.getAmount());
        if (bcRemainingAmount.compareTo(BigDecimal.ZERO) > 0) {
          JsonRespBO jsonRespBO = blockchainService.transferToken(loggedInUser, loggedInUser.getWalletAddress(), investor.getWalletAddress(), bcToken, dto.getAmount().toBigInteger());
          String bcTxId = jsonRespBO.getTxId();
          TransferQueryRespBO bcTxDetail = blockchainService.getTransferDetails(bcTxId);
          if (bcTxDetail != null) {
            // Save balance for sender wallet
            bcTokenBalance.setBalanceAmount(bcRemainingAmount);
            balanceService.createBalance(bcTokenBalance);

            // Save balance for receiver wallet
            // Creates new entry in table if not present
            Balance receiverBalance = balanceService.fetchBalanceByTokenCodeAndId(dto.getBcTokenCode(), investor.getId());
            if (receiverBalance == null) {
              Balance newBalance = new Balance();
              newBalance.setBalanceAmount(dto.getAmount());
              newBalance.setTokenCode(dto.getBcTokenCode());
              newBalance.setTokenType(TokenType.BC_TOKEN);
              newBalance.setUser(investor);
              balanceService.createBalance(newBalance);
            } else {
              BigDecimal newAmount = receiverBalance.getBalanceAmount().add(dto.getAmount());
              receiverBalance.setBalanceAmount(newAmount);
              balanceService.createBalance(receiverBalance);
            }
          }

          TransactionHistory tx = new TransactionHistory();
          tx.setTokenContractAddress(bcToken.getTokenContractAddress());
          tx.setAmount(dto.getAmount());
          tx.setFromAddress(loggedInUser.getWalletAddress());
          tx.setToAddress(investor.getWalletAddress());
          tx.setBlockHeight(bcTxDetail.getBlockHeight());
          // Transaction type?
          tx.setStatus(TransactionStatus.REDEMPTION);
          tx.setCtxId(bcTxId);
          tx.setTokenCode(bcToken.getTokenCode());
          tx.setTokenType(TokenType.BC_TOKEN);
          tx.setTokenId(bcToken.getId());
          transactionHistoryService.save(tx);
        }
      }
    }  catch (Exception e){
      LOGGER.error("Exception in opsTrade().", e);
      throw new ServerErrorException("Exception in opsTrade().", e);
    }
  }

  @Transactional(rollbackFor = ServerErrorException.class)
  public void croTrade(TransferBCTokenToOpsRequestDto dto) throws ServerErrorException {
    LOGGER.debug("Entering croTrade().");
    try {
      String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
      User loggedInUser = userService.fetchByUsername(username);
      BaseCurrencyToken bcToken = repository.findByTokenCode(dto.getBcTokenCode());
      User sender = userService.fetchByWalletAddress(dto.getSenderAddress());
      User recipient = userService.fetchByWalletAddress(dto.getRecepientAddress());
      Balance bcTokenBalance = balanceService.fetchBalanceByTokenCodeAndId(dto.getBcTokenCode(), sender.getId());
      if (bcTokenBalance != null) {
        BigDecimal bcRemainingAmount = bcTokenBalance.getBalanceAmount().subtract(dto.getAmount());
        if (bcRemainingAmount.compareTo(BigDecimal.ZERO) > 0) {
          JsonRespBO jsonRespBO = blockchainService.transferToken(loggedInUser, sender.getWalletAddress(), recipient.getWalletAddress(), bcToken, dto.getAmount().toBigInteger());
          String bcTxId = jsonRespBO.getTxId();
          TransferQueryRespBO bcTxDetail = blockchainService.getTransferDetails(bcTxId);
          if (bcTxDetail != null) {
            // Save balance for sender wallet
            bcTokenBalance.setBalanceAmount(bcRemainingAmount);
            balanceService.createBalance(bcTokenBalance);

            // Save balance for receiver wallet
            // Creates new entry in table if not present
            Balance receiverBalance = balanceService.fetchBalanceByTokenCodeAndId(dto.getBcTokenCode(), recipient.getId());
            if (receiverBalance == null) {
              Balance newBalance = new Balance();
              newBalance.setBalanceAmount(dto.getAmount());
              newBalance.setTokenCode(dto.getBcTokenCode());
              newBalance.setTokenType(TokenType.BC_TOKEN);
              newBalance.setUser(recipient);
              balanceService.createBalance(newBalance);
            } else {
              BigDecimal newAmount = receiverBalance.getBalanceAmount().add(dto.getAmount());
              receiverBalance.setBalanceAmount(newAmount);
              balanceService.createBalance(receiverBalance);
            }
          }

          TransactionHistory tx = new TransactionHistory();
          tx.setTokenContractAddress(bcToken.getTokenContractAddress());
          tx.setAmount(dto.getAmount());
          tx.setFromAddress(sender.getWalletAddress());
          tx.setToAddress(recipient.getWalletAddress());
          tx.setBlockHeight(bcTxDetail.getBlockHeight());
          tx.setStatus(TransactionStatus.FUND_TRANSFER);
          tx.setCtxId(bcTxId);
          tx.setTokenCode(bcToken.getTokenCode());
          tx.setTokenType(TokenType.BC_TOKEN);
          tx.setTokenId(bcToken.getId());
          transactionHistoryService.save(tx);
        }
      }
    }  catch (Exception e){
      LOGGER.error("Exception in croTrade().", e);
      throw new ServerErrorException("Exception in croTrade().", e);
    }
  }

}
