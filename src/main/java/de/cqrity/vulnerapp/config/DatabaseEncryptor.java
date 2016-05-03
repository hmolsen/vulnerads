package de.cqrity.vulnerapp.config;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DatabaseEncryptor {

    private static DatabaseEncryptor encryptor;
    private Cipher aesEncryptor;
    private Cipher aesDecryptor;

    private DatabaseEncryptor() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey key = keyGenerator.generateKey();
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            aesEncryptor = Cipher.getInstance("AES");
            aesEncryptor.init(Cipher.ENCRYPT_MODE, keySpec);
            aesDecryptor = Cipher.getInstance("AES");
            aesDecryptor.init(Cipher.DECRYPT_MODE, keySpec);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseEncryptor getInstance() {
        if (encryptor == null) {
            encryptor = new DatabaseEncryptor();
        }

        return encryptor;
    }

    public byte[] encrypt(String unencrypted) {
        try {
            return aesEncryptor.doFinal(unencrypted.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(byte[] encrypted) {
        try {
            return new String(aesDecryptor.doFinal(encrypted));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
