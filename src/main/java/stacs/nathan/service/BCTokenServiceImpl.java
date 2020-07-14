package stacs.nathan.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hashstacs.sdk.response.base.JsonRespBO;
import hashstacs.sdk.response.blockchain.token.TokenQueryRespBO;
import hashstacs.sdk.response.blockchain.token.TransferQueryRespBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.request.TransferBCTokenRequestDto;
import stacs.nathan.dto.response.BCTokenResponseDto;
import stacs.nathan.dto.response.CreateBCTokenInitDto;
import stacs.nathan.entity.*;
import stacs.nathan.repository.BCTokenRepository;
import stacs.nathan.repository.BalanceRepository;
import stacs.nathan.repository.TransactionRepository;
import stacs.nathan.utils.enums.BCTokenStatus;
import stacs.nathan.utils.enums.CodeType;
import stacs.nathan.utils.enums.TokenType;
import stacs.nathan.utils.enums.TransactionStatus;

import javax.persistence.TransactionRequiredException;
import java.math.BigDecimal;
import java.math.BigInteger;
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
  BalanceRepository balanceRepository;

  @Autowired
  TransactionRepository transactionRepository;

  public CreateBCTokenInitDto fetchInitForm(){
    String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    User loggedInUser = userService.fetchByUsername(username);
    CreateBCTokenInitDto dto = new CreateBCTokenInitDto();
    dto.setUnderlying(codeValueService.findByType(CodeType.UNDERLYING));
    dto.setIssuingAddress(loggedInUser.getWalletAddress());
    return dto;
  }

  @Transactional(rollbackFor = ServerErrorException.class)
  public void createBCToken(BCTokenRequestDto dto) throws ServerErrorException, BadRequestException {
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
      token.setCreatedBy(username);
      JsonRespBO jsonRespBO = blockchainService.createToken(loggedInUser, TokenType.BC_TOKEN, dto.getAmount());
      if (jsonRespBO == null) {
        token.setStatus(BCTokenStatus.CHAIN_UNAVAILABLE);
        repository.save(token);
      } else {
        processAvailableChain(token, jsonRespBO);
      }
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

  public List<BCTokenResponseDto> fetchAllByIssuerAddress(String issuerAddress) throws ServerErrorException {
    LOGGER.debug("Entering fetchAllBCTokens().");
    try {
      return repository.fetchAllByIssuerAddress(issuerAddress, BCTokenStatus.OPEN);
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
        JsonRespBO jsonRespBO = blockchainService.createToken(token.getUser(), TokenType.BC_TOKEN, token.getAmount());
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

  public void transferBCToken(TransferBCTokenRequestDto dto) throws ServerErrorException {
    LOGGER.debug("Entering transferBCToken().");
    String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    User loggedInUser = userService.fetchByUsername(username);
    BaseCurrencyToken bcToken = repository.findByTokenCode(dto.getBcTokenCode());
    User investor = userService.fetchByWalletAddress(dto.getInvestorWalletAddress());
    Balance bcTokenBalance = balanceService.fetchBalanceByTokenCodeAndId(dto.getBcTokenCode(), investor.getId());
    BigDecimal remainingAmount = bcTokenBalance.getBalanceAmount().subtract(dto.getAmount());
    if (!(remainingAmount.compareTo(BigDecimal.ZERO) < 0)) {
      JsonRespBO jsonRespBO = blockchainService.transferToken(investor, loggedInUser.getWalletAddress(), bcToken, dto.getAmount().toBigInteger());
      String txId = jsonRespBO.getTxId();
      TransferQueryRespBO txDetail = blockchainService.getTransferDetails(txId);
      if (txDetail != null) {
        // Save balance for investor wallet
        bcTokenBalance.setBalanceAmount(remainingAmount);
        balanceRepository.save(bcTokenBalance);

        // Save balance for ops wallet
        Balance opsBalance = balanceService.fetchBalanceByTokenCodeAndId(dto.getBcTokenCode(), loggedInUser.getId());
        if (opsBalance == null) {
          Balance newBalance = new Balance();
          newBalance.setBalanceAmount(dto.getAmount());
          newBalance.setTokenCode(dto.getBcTokenCode());
          newBalance.setTokenType(TokenType.BC_TOKEN);
          newBalance.setUser(loggedInUser);
          balanceRepository.save(newBalance);
        } else {
          BigDecimal newAmount = opsBalance.getBalanceAmount().add(dto.getAmount());
          opsBalance.setBalanceAmount(newAmount);
          balanceRepository.save(opsBalance);
        }

        // Update transaction history table
        TransactionHistory tx = new TransactionHistory();
        tx.setTokenContractAddress(bcToken.getTokenContractAddress());
        tx.setAmount(dto.getAmount());
        tx.setFromAddress(investor.getWalletAddress());
        tx.setToAddress(loggedInUser.getWalletAddress());
        tx.setBlockHeight(txDetail.getBlockHeight());
        tx.setStatus(TransactionStatus.KNOCK_OUT);
        tx.setCtxId(txId);
        tx.setTokenType(TokenType.SP_TOKEN);
        tx.setTokenId(bcToken.getId());
        tx.setCreatedBy(loggedInUser.getUsername());
        transactionRepository.save(tx);
      }
    }
    else {
      throw new ServerErrorException("Insufficient balance for transfer");
    }
  }

}
