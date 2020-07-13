package stacs.nathan.service;

import hashstacs.sdk.dto.token.Token;
import hashstacs.sdk.dto.token.Transfer;
import hashstacs.sdk.dto.token.Wallet;
import hashstacs.sdk.request.blockchain.token.IssueTokenReqBO;
import hashstacs.sdk.request.blockchain.token.TransferTokenReqBO;
import hashstacs.sdk.response.base.JsonRespBO;
import hashstacs.sdk.response.blockchain.token.TokenQueryRespBO;
import hashstacs.sdk.response.blockchain.token.TransferQueryRespBO;
import hashstacs.sdk.util.ChainConnector;
import hashstacs.sdk.util.StacsAPIUtil;
import hashstacs.sdk.util.StacsUtil;
import io.stacs.nav.crypto.StacsECKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import stacs.nathan.core.encryption.CryptoCipher;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.entity.BaseTokenEntity;
import stacs.nathan.entity.User;
import stacs.nathan.utils.enums.TokenType;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Service
public class BlockchainService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainService.class);

  @Value("${stacs.policy}")
  private String policy;
  @Value("${stacs.bdcode}")
  private String bdCode;
  @Value("${stacs.contract.method}")
  private String contractMethod;
  @Value("${stacs.balance.method}")
  private String balanceMethod;
  @Value("${code.location}")
  private String codeLocation;
  @Value("${stacs.config.props}")
  private String configProps;
  @Value("${stacs.chain-query.wait-time}")
  private int queryWaitTime;
  @Value("${stacs.chain-query.max-retries}")
  private int queryMaxRetries;
  @Value("${stacs.bdfunction}")
  private String bdFunction;
  @Value("${stacs.transfer.method}")
  private String transferMethod;

  private static StringBuilder merchantAesKey = new StringBuilder();
  private static StringBuilder domainMerchantId = new StringBuilder();
  private static StringBuilder domainGateway = new StringBuilder();
  private static ChainConnector chainConnector;

  @Autowired
  private CryptoCipher cipher;

  private void initChainConnector(){
    merchantAesKey.append(StacsUtil.getConfigProperty(configProps,StacsUtil.ConfigEnums.MERCHANT_AESKEY));
    domainMerchantId.append(StacsUtil.getConfigProperty(configProps,StacsUtil.ConfigEnums.DOMAIN_MERCHANTID));
    domainGateway.append(StacsUtil.getConfigProperty(configProps,StacsUtil.ConfigEnums.DOMAIN_GATEWAY));
    chainConnector = ChainConnector.initConn(merchantAesKey.toString(), domainMerchantId.toString(), domainGateway.toString());
  }

  public void createWallet(User user){
    LOGGER.debug("Entering createWallet().");
    try{
      StacsECKey submitterKey = new StacsECKey();
      user.setPrivateKey(cipher.encrypt(submitterKey.getHexPriKey()));
      user.setWalletAddress(submitterKey.getHexAddress());
    }catch (Exception e){
      LOGGER.error("Exception in createWallet().", e);
    }
  }

  public JsonRespBO createToken(User user, TokenType tokenType, BigDecimal quantity) throws ServerErrorException {
    LOGGER.debug("Entering createToken().");
    initChainConnector();
    StacsECKey authKey = new StacsECKey();
    //StacsECKey tokenCustodyAddress = new StacsECKey();
    StacsECKey contractAddress = new StacsECKey();

    Token token = new Token(bdCode);
    token.setPolicyName(policy);
    token.setContractMainMethod(contractMethod);
    token.setContractCode(StacsAPIUtil.txt2String(new File(codeLocation)));
    token.setSubmitterAddress(user.getWalletAddress());
    token.setAuthAddress(authKey.getHexAddress());
    token.setContractAddress(contractAddress.getHexAddress());
    token.setTokenCustodyAddress(user.getWalletAddress());
    token.setQtyNumOfDecimals(8);
    token.setTokenCode(tokenType.getCode() + "_" + new Date().getTime());
    token.setTokenName(tokenType.getValue());
    token.setPolicyName(policy);
    token.setTotalQuantity(quantity.toBigInteger());

    IssueTokenReqBO issueToken = new IssueTokenReqBO(token);
    issueToken.generateTxId();

    StringBuilder signaturePayload = new StringBuilder();
    signaturePayload.append(issueToken.generateOfflinePayloadForSigning());

    //signing
    String signature = StacsECKey.fromPrivate(Hex.decode(cipher.decrypt(user.getPrivateKey().trim()))).signMessage(signaturePayload.toString());
    StacsECKey.verify(signaturePayload.toString(), signature, user.getWalletAddress());

    //after signing
    Token afterSignToken = new Token(token.getReqObj());
    afterSignToken.setSubmitterSignature(signature);
    JsonRespBO jsonRespBO = null;
    try {
      jsonRespBO = chainConnector.issueToken(afterSignToken);
      if(jsonRespBO == null || !jsonRespBO.getIsSuccessful()){
        return jsonRespBO;
      }
    } catch (Exception e) {
      LOGGER.error("Issue token in blockchain is not successful");
      return jsonRespBO;
    }
    return jsonRespBO;
  }

  public TokenQueryRespBO getTxDetails(String txId){
    initChainConnector();
    for(int i = 0; i < queryMaxRetries; i++) {
      try {
        Thread.sleep(queryWaitTime);
        TokenQueryRespBO txDetailRespBO = (TokenQueryRespBO) chainConnector.queryDetailsByTxId(txId);
        if(txDetailRespBO != null && txDetailRespBO.getBlockHeight() != null) {
          return txDetailRespBO;
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public TransferQueryRespBO getTransferDetails(String txId) {
    initChainConnector();
    for (int i = 0; i < queryMaxRetries; i++) {
      try {
        Thread.sleep(queryWaitTime);
        TransferQueryRespBO transferQueryRespBO = (TransferQueryRespBO) chainConnector.queryDetailsByTxId(txId);
        return transferQueryRespBO;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public JsonRespBO transferToken(User user, String recipientAddress, BaseTokenEntity token, BigInteger quantity) throws ServerErrorException {
    initChainConnector();
    Transfer transferObj = new Transfer(bdCode);
    transferObj.setBdFunctionName(bdFunction);
    transferObj.setSubmitterAddress(user.getWalletAddress());
    transferObj.setSmartContractMethod(transferMethod);
    transferObj.setSenderAddress(user.getWalletAddress());
    transferObj.setRecipientAddress(recipientAddress);
    transferObj.setTokenCode(token.getTokenCode());
    transferObj.setTokenContractAddress(token.getTokenContractAddress());
    transferObj.setTransferQuantity(quantity);

    TransferTokenReqBO transferReq = new TransferTokenReqBO(transferObj);
    transferReq.generateTxId();
    StringBuilder signaturePayload = new StringBuilder();
    signaturePayload.append(transferReq.generateOfflinePayloadForSigning());
    String signature = StacsECKey.fromPrivate(Hex.decode(cipher.decrypt(user.getPrivateKey().trim()))).signMessage(signaturePayload.toString());

    Transfer afterSignTransfer = new Transfer(transferObj.getReqObj());
    afterSignTransfer.setSubmitterSignature(signature);
    JsonRespBO jsonRespBO = null;
    try {
      jsonRespBO = chainConnector.transferToken(afterSignTransfer);
      if (jsonRespBO == null || !jsonRespBO.getIsSuccessful()) {
        return jsonRespBO;
      }
    } catch (Exception e) {
      LOGGER.error("Transfer token in blockchain is not successful");
      return jsonRespBO;
    }
    return jsonRespBO;

  }

  public BigDecimal getWalletBalance(BaseTokenEntity token, String walletAddress) {
    initChainConnector();
    Wallet walletBalance = new Wallet();
    walletBalance.setTokenContractAddress(token.getTokenContractAddress());
    walletBalance.setTokenContractBalanceMethod(balanceMethod);
    walletBalance.setWalletAddress(walletAddress);

    return chainConnector.getWalletBalance(walletBalance);
  }

}
