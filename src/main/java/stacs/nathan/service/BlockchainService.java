package stacs.nathan.service;

import hashstacs.sdk.dto.Token;
import hashstacs.sdk.request.blockchain.IssueTokenReqBO;
import hashstacs.sdk.util.ChainConnector;
import io.stacs.nav.crypto.StacsECKey;
import org.spongycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;
import stacs.nathan.entity.BaseCurrencyToken;
import stacs.nathan.entity.User;
import stacs.nathan.utils.enums.TokenType;
import java.math.BigInteger;


@Service
public class BlockchainService {

  private static String DEFAULT_BD_CODE = "NATHAN";
  private static String DEFAULT_POLICY = "DEFAULT_SYNC_POLICY";

  private static ChainConnector chainConnector;

  public void createWallet(User user){
    StacsECKey submitterKey = new StacsECKey();
    user.setPrivateKey(submitterKey.getHexPriKey());
    user.setWalletAddress(submitterKey.getHexAddress());
    createBaseCurrencyToken(user, new BaseCurrencyToken());
  }

  public void createBaseCurrencyToken(User user, BaseCurrencyToken bcToken){
    Token token = new Token(DEFAULT_BD_CODE);
    token.setTokenCode(TokenType.SP_TOKEN.getCode());
    token.setTokenName(TokenType.SP_TOKEN.getValue());
    token.setPolicyName(DEFAULT_POLICY);
    token.setTotalQuantity(new BigInteger(String.valueOf(100000)));

    IssueTokenReqBO issueToken = new IssueTokenReqBO(token);
    bcToken.setCtxId(issueToken.generateTxId());

    StringBuilder signaturePayload = new StringBuilder();
    signaturePayload.append(issueToken.generateOfflinePayloadForSigning());

    //signing
    String signature = StacsECKey.fromPrivate(Hex.decode(user.getPrivateKey().trim())).signMessage(signaturePayload.toString());
    StacsECKey.verify(signaturePayload.toString(), signature, user.getWalletAddress());

    //after signing
    Token afterSignToken = new Token(token.getReqObj());
    afterSignToken.setSubmitterSignature(signature);
    chainConnector.issueToken(afterSignToken);
  }

}
