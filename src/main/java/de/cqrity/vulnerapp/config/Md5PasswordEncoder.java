package de.cqrity.vulnerapp.config;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

// Replaces the removed MessageDigestPasswordEncoder("MD5") from Spring Security 5.
// Intentionally insecure — MD5 password hashing is a deliberate vulnerability in this training app.
public class Md5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtils.md5Hex(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String salt = extractSalt(encodedPassword);
        String expected = salt + DigestUtils.md5Hex(rawPassword.toString() + salt);
        return expected.equals(encodedPassword);
    }

    // Extracts the "{salt}" prefix used by the legacy MessageDigestPasswordEncoder format.
    // Passwords stored without a prefix (plain MD5 hex) yield an empty salt.
    private String extractSalt(String encodedPassword) {
        if (!encodedPassword.startsWith("{")) return "";
        int end = encodedPassword.indexOf('}');
        return end < 0 ? "" : encodedPassword.substring(0, end + 1);
    }
}