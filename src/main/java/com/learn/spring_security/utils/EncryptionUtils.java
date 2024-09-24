package com.learn.spring_security.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtils {

    private static final String ALGORITHM = "AES";

    private static final Logger logger = LoggerFactory.getLogger(EncryptionUtils.class);

    private static final byte[] SECRET_KEY = "key@Pukar!&Day17".getBytes();

    public static String encrypt(String value) throws Exception {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            logger.error("::: ERROR: cannot encrypt due to: {}", e.getMessage());
            throw e;
        }
    }

    public static String decrypt(String encryptedValue) throws Exception {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
            return new String(decrypted);
        } catch (Exception e) {
            logger.error("::: ERROR: cannot decrypt due to: {}", e.getMessage());
            throw e;
        }
    }
}
