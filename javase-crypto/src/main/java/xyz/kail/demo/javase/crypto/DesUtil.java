package xyz.kail.demo.javase.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DesUtil {

    private static final byte[] IV = {1, 2, 3, 4, 5, 6, 7, 8};

    public static String decryptDesWithBase64(String encryptedString, String decryptKey) throws Exception {
        if (null == encryptedString || encryptedString.length() <= 0) {
            return "";
        }
        byte[] encryptedData = Base64.getDecoder().decode(encryptedString.replaceAll(" ", "+").getBytes());
        return decryptDES(encryptedData, decryptKey);
    }

    public static String decryptDES(byte[] encryptedData, String decryptKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(IV);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(StandardCharsets.UTF_8), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

}
