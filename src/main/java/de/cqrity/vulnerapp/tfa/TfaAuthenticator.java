package de.cqrity.vulnerapp.tfa;

import de.cqrity.vulnerapp.config.DatabaseEncryptor;
import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TfaAuthenticator {

    private String decryptedTfaSecret;

    private int variance;

    public TfaAuthenticator(DatabaseEncryptor databaseEncryptor, byte[] encryptedTfaSecret, int variance) {
        decryptedTfaSecret = databaseEncryptor.decrypt(encryptedTfaSecret);
        this.variance = variance;
    }

    public boolean verifyCode(int submittedTfaCode) {
        long timeIndex = System.currentTimeMillis() / 1000 / 30;
        byte[] secretBytes = new Base32().decode(decryptedTfaSecret);
        for (int i = -variance; i <= variance; i++) {
            long calculatedCode = 0;
            try {
                calculatedCode = getCode(secretBytes, timeIndex + i);
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                e.printStackTrace();
                return false;
            }
            if (calculatedCode == submittedTfaCode) {
                return true;
            }
        }
        return false;
    }

    private long getCode(byte[] secret, long timeIndex) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signKey = new SecretKeySpec(secret, "HmacSHA1");
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(timeIndex);
        byte[] timeBytes = buffer.array();
        Mac mac = null;
        mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(timeBytes);
        int offset = hash[19] & 0xf;
        long truncatedHash = hash[offset] & 0x7f;
        for (int i = 1; i < 4; i++) {
            truncatedHash <<= 8;
            truncatedHash |= hash[offset + i] & 0xff;
        }
        return truncatedHash % 1000000;
    }
}
