package stacs.nathan.service;

import hashstacs.sdk.dto.Token;
import hashstacs.sdk.request.blockchain.IssueTokenReqBO;
import hashstacs.sdk.response.base.JsonRespBO;
import hashstacs.sdk.response.blockchain.TokenQueryRespBO;
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
  @Value("${code.location}")
  private String codeLocation;
  @Value("${stacs.config.props}")
  private String configProps;
  @Value("${stacs.chain-query.wait-time}")
  private int queryWaitTime;
  @Value("${stacs.chain-query.max-retries}")
  private int queryMaxRetries;

  private static StringBuilder merchantAesKey = new StringBuilder();
  private static StringBuilder domainMerchantId = new StringBuilder();
  private static StringBuilder domainGateway = new StringBuilder();
  private static ChainConnector chainConnector;

  @Autowired
  private CryptoCipher cipher;

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
    merchantAesKey.append(StacsUtil.getConfigProperty(configProps,StacsUtil.ConfigEnums.MERCHANT_AESKEY));
    domainMerchantId.append(StacsUtil.getConfigProperty(configProps,StacsUtil.ConfigEnums.DOMAIN_MERCHANTID));
    domainGateway.append(StacsUtil.getConfigProperty(configProps,StacsUtil.ConfigEnums.DOMAIN_GATEWAY));

    chainConnector = ChainConnector.initConn(merchantAesKey.toString(), domainMerchantId.toString(), domainGateway.toString());
    StacsECKey authKey = new StacsECKey();
    StacsECKey tokenCustodyAddress = new StacsECKey();
    StacsECKey contractAddress = new StacsECKey();

    Token token = new Token(bdCode);
    token.setPolicyName(policy);
    token.setContractMainMethod(contractMethod);
    token.setContractCode(StacsAPIUtil.txt2String(new File(codeLocation)));
    token.setSubmitterAddress(user.getWalletAddress());
    token.setAuthAddress(authKey.getHexAddress());
    token.setContractAddress(contractAddress.getHexAddress());
    token.setTokenCustodyAddress(tokenCustodyAddress.getHexAddress());
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
    JsonRespBO jsonRespBO = chainConnector.issueToken(afterSignToken);
    if(!jsonRespBO.getIsSuccessful()){
      LOGGER.error("Issue token in blockchain is not successful");
      throw new ServerErrorException("Exception in createToken().");
    }
    return jsonRespBO;
  }

  public TokenQueryRespBO getTxDetails(String txId){
    String blockHeight = null;
    chainConnector = ChainConnector.initConn(merchantAesKey.toString(), domainMerchantId.toString(), domainGateway.toString());
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

}
