import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import stacs.nathan.core.encryption.CryptoCipher;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.utils.enums.FxCurrency;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

public class Test {
//    static String cipherKey1 = "ioUt^j6VvR)d^]Uk";
//    static String cipherKey2 = "r3W0ippUj.?Xc9P:";
//
//    static IvParameterSpec ivParameterSpec;
//    static SecretKeySpec secretKeySpec;
//    static Cipher cipher;
//
//    static void initCipher() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
//        ivParameterSpec = new IvParameterSpec(cipherKey1.getBytes("UTF-8"));
//        secretKeySpec = new SecretKeySpec(cipherKey2.getBytes("UTF-8"), "AES");
//        cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//        System.out.println(cipherKey1);
//    }
//
//    static String encrypt(String toBeEncrypt) throws ServerErrorException {
//        LOGGER.debug("Entering encrypt().");
//        try{
//            initCipher();
//            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
//            byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes());
//            return Base64.encodeBase64String(encrypted);
//        }catch (Exception e){
//            LOGGER.error("Exception in encrypt().", e);
//            throw new ServerErrorException("Exception in encrypt().", e);
//        }
//    }
//
//    public static void main (String[] args) {
//        try {
//            System.out.println(encrypt("3286adaad94184bc7f891f6c12b098e315ca85ed788ec9e407e4084d2191345c"));
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }

    public static void main (String[] args) {
        FxCurrency currency = FxCurrency.valueOf("GBP_USD");
        System.out.println("currnecy: " + currency.getValue());
    }
}
