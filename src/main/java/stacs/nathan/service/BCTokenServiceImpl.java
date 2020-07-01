package stacs.nathan.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hashstacs.sdk.response.base.JsonRespBO;
import hashstacs.sdk.response.blockchain.token.TokenQueryRespBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.response.BCTokenResponseDto;
import stacs.nathan.dto.response.CreateBCTokenInitDto;
import stacs.nathan.entity.BaseCurrencyToken;
import stacs.nathan.entity.User;
import stacs.nathan.repository.BCTokenRepository;
import stacs.nathan.utils.enums.BCTokenStatus;
import stacs.nathan.utils.enums.CodeType;
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

  @Autowired
  CodeValueService codeValueService;

  public CreateBCTokenInitDto fetchInitForm(){
    String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    User loggedInUser = userService.fetchByUsername(username);
    CreateBCTokenInitDto dto = new CreateBCTokenInitDto();
    dto.setUnderlying(codeValueService.findByType(CodeType.UNDERLYING));
    dto.setIssuingAddress(loggedInUser.getWalletAddress());
    return dto;
  }

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
      token.setStatus(BCTokenStatus.CONFIRMED_IN_CHAIN);
      repository.save(token);
    }
  }

  public List<BCTokenResponseDto> fetchAllByIssuerAddress(String issuerAddress) throws ServerErrorException {
    LOGGER.debug("Entering fetchAllBCTokens().");
    try {
      return repository.fetchAllByIssuerAddress(issuerAddress, BCTokenStatus.CONFIRMED_IN_CHAIN);
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
    List<BaseCurrencyToken> tokens = repository.findByStatus(BCTokenStatus.UNCONFIRMED_IN_CHAIN);
    for (BaseCurrencyToken token : tokens) {
      TokenQueryRespBO txDetail = blockchainService.getTxDetails(token.getCtxId());
      if (txDetail != null) {
        token.setTokenContractAddress(txDetail.getTokenInfo().getContractAddress());
        token.setBlockHeight(txDetail.getBlockHeight());
        token.setStatus(BCTokenStatus.CONFIRMED_IN_CHAIN);
        repository.save(token);
      }
      blockchainService.getTxDetails(token.getCtxId());
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

}
