package stacs.nathan.service;

import hashstacs.sdk.dto.Token;
import hashstacs.sdk.request.blockchain.IssueTokenReqBO;
import hashstacs.sdk.util.ChainConnector;
import io.stacs.nav.crypto.StacsECKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.core.encryption.CryptoCipher;
import stacs.nathan.entity.BaseCurrencyToken;
import stacs.nathan.entity.User;
import stacs.nathan.utils.enums.TokenType;
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

  public String createToken(User user, TokenType tokenType, int currency){
    LOGGER.debug("Entering createBaseCurrencyToken().");
    String ctxId;
    Token token = new Token(DEFAULT_BD_CODE);
    token.setTokenCode(tokenType.getCode());
    token.setTokenName(tokenType.getValue());
    token.setPolicyName(DEFAULT_POLICY);
    token.setFeeCurrency(Integer.toString(currency));
    token.setTotalQuantity(new BigInteger(String.valueOf(100000)));

    IssueTokenReqBO issueToken = new IssueTokenReqBO(token);
    ctxId = issueToken.generateTxId();

    StringBuilder signaturePayload = new StringBuilder();
    signaturePayload.append(issueToken.generateOfflinePayloadForSigning());

    //signing
    String signature = StacsECKey.fromPrivate(Hex.decode(user.getPrivateKey().trim())).signMessage(signaturePayload.toString());
    StacsECKey.verify(signaturePayload.toString(), signature, user.getWalletAddress());

    //after signing
    Token afterSignToken = new Token(token.getReqObj());
    afterSignToken.setSubmitterSignature(signature);
    chainConnector.issueToken(afterSignToken);
    return ctxId;
  }



}
