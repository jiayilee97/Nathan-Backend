package stacs.nathan.service;

import hashstacs.sdk.dto.Token;
import hashstacs.sdk.request.blockchain.IssueTokenReqBO;
import hashstacs.sdk.response.base.JsonRespBO;
import hashstacs.sdk.response.blockchain.TokenQueryRespBO;
import hashstacs.sdk.response.blockchain.TxDetailRespBO;
import hashstacs.sdk.util.ChainConnector;
import hashstacs.sdk.util.StacsAPIUtil;
import hashstacs.sdk.util.StacsUtil;
import io.stacs.nav.crypto.StacsECKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.core.encryption.CryptoCipher;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.entity.User;
import stacs.nathan.utils.enums.TokenType;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class BlockchainService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainService.class);
  private static String DEFAULT_BD_CODE = "NATHAN_DEV";
  private static String DEFAULT_POLICY = "DEFAULT_SYNC_POLICY";
  private static String DEFAULT_CONTRACT_METHOD ="TransferDemo(address,string,string,uint,uint8)";
  private static String CODE_LOCATION = "./src/main/java/stacs/nathan/resources/solidity/SampleToken.txt";
  private String SUBMITTER_ADDRESS ="177f03aefabb6dfc07f189ddf6d0d48c2f60cdbf";
  private String BD_PRIKEY = "bbb43be030237c818bea2a5b808e872f432d1e83e6776f88b66a30d00956188c";

  private static StringBuilder merchantAesKey = new StringBuilder();
  private static StringBuilder domainMerchantId = new StringBuilder();
  private static StringBuilder domainGateway = new StringBuilder();
  private static String CONFIG_PROPERTIES = "config.properties";
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

  public JsonRespBO createToken(User user, TokenType tokenType, String currency, BigDecimal quantity) throws ServerErrorException {
    LOGGER.debug("Entering createToken().");
    merchantAesKey.append(StacsUtil.getConfigProperty(CONFIG_PROPERTIES,StacsUtil.ConfigEnums.MERCHANT_AESKEY));
    domainMerchantId.append(StacsUtil.getConfigProperty(CONFIG_PROPERTIES,StacsUtil.ConfigEnums.DOMAIN_MERCHANTID));
    domainGateway.append(StacsUtil.getConfigProperty(CONFIG_PROPERTIES,StacsUtil.ConfigEnums.DOMAIN_GATEWAY));

    chainConnector = ChainConnector.initConn(merchantAesKey.toString(), domainMerchantId.toString(), domainGateway.toString());
    StacsECKey authKey = new StacsECKey();
    StacsECKey tokenCustodyAddress = new StacsECKey();
    StacsECKey contractAddress = new StacsECKey();

    Token token = new Token(DEFAULT_BD_CODE);
    token.setPolicyName(DEFAULT_POLICY);
    token.setContractMainMethod(DEFAULT_CONTRACT_METHOD);
    token.setContractCode(StacsAPIUtil.txt2String(new File(CODE_LOCATION)));
    token.setSubmitterAddress(SUBMITTER_ADDRESS);
    token.setAuthAddress(authKey.getHexAddress());
    token.setContractAddress(contractAddress.getHexAddress());
    token.setTokenCustodyAddress(tokenCustodyAddress.getHexAddress());
    token.setQtyNumOfDecimals(8);
    token.setTokenCode(tokenType.getCode());
    token.setTokenName(tokenType.getValue());
    token.setPolicyName(DEFAULT_POLICY);
    token.setFeeCurrency(currency);
    token.setTotalQuantity(new BigInteger(String.valueOf(quantity)));

    IssueTokenReqBO issueToken = new IssueTokenReqBO(token);
    issueToken.generateTxId();

    StringBuilder signaturePayload = new StringBuilder();
    signaturePayload.append(issueToken.generateOfflinePayloadForSigning());

    //signing
    // Add private key into DB and replace signature
    String signature = StacsECKey.fromPrivate(Hex.decode(BD_PRIKEY.trim())).signMessage(signaturePayload.toString());
    StacsECKey.verify(signaturePayload.toString(), signature, SUBMITTER_ADDRESS);

    //after signing
    Token afterSignToken = new Token(token.getReqObj());
    afterSignToken.setSubmitterSignature(signature);
    System.out.println("txid:"+ afterSignToken.getTxId());
    return chainConnector.issueToken(afterSignToken);
  }

  public TokenQueryRespBO getTxDetails(String txId){
    return (TokenQueryRespBO) chainConnector.queryDetailsByTxId(txId);
  }

}
