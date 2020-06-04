package stacs.nathan.service;

import hashstacs.sdk.dto.Token;
import hashstacs.sdk.request.blockchain.IssueTokenReqBO;
import hashstacs.sdk.response.base.JsonRespBO;
import hashstacs.sdk.response.blockchain.TxDetailRespBO;
import hashstacs.sdk.util.ChainConnector;
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
import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class BlockchainService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainService.class);
  private static String DEFAULT_BD_CODE = "NATHAN";
  private static String DEFAULT_POLICY = "DEFAULT_SYNC_POLICY";
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
    Token token = new Token(DEFAULT_BD_CODE);
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
    String signature = StacsECKey.fromPrivate(Hex.decode(cipher.decrypt(user.getPrivateKey().trim()))).signMessage(signaturePayload.toString());
    StacsECKey.verify(signaturePayload.toString(), signature, user.getWalletAddress());

    //after signing
    Token afterSignToken = new Token(token.getReqObj());
    afterSignToken.setSubmitterSignature(signature);
    return chainConnector.issueToken(afterSignToken);
  }

  public TxDetailRespBO getTxDetails(String txId){
    return chainConnector.queryDetailsByTxId(txId);
  }



}
