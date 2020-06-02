package stacs.nathan.core.encryption;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Service
public class CryptoCipher {
  private static final Logger LOGGER = LoggerFactory.getLogger(CryptoCipher.class);

  @Value("${cipher.secret.key1}")
  private String cipherKey1;

  @Value("${cipher.secret.key2}")
  private String cipherKey2;

  private IvParameterSpec ivParameterSpec;
  private SecretKeySpec secretKeySpec;
  private Cipher cipher;

  public void initCipher() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
    ivParameterSpec = new IvParameterSpec(cipherKey1.getBytes("UTF-8"));
    secretKeySpec = new SecretKeySpec(cipherKey2.getBytes("UTF-8"), "AES");
    cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
  }

  public String encrypt(String toBeEncrypt) throws ServerErrorException {
    LOGGER.debug("Entering encrypt().");
    try{
      initCipher();
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
      byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes());
      return Base64.encodeBase64String(encrypted);
    }catch (Exception e){
      LOGGER.error("Exception in encrypt().", e);
      throw new ServerErrorException("Exception in encrypt().", e);
    }
  }

  public String decrypt(String encrypted) throws ServerErrorException {
    LOGGER.debug("Entering decrypt().");
    try{
      initCipher();
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
      byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
      return new String(decryptedBytes);
    }catch (Exception e){
      LOGGER.error("Exception in decrypt().", e);
      throw new ServerErrorException("Exception in decrypt().", e);
    }
  }

}
