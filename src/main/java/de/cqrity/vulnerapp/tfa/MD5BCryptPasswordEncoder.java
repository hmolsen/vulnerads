package de.cqrity.vulnerapp.tfa;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MD5BCryptPasswordEncoder extends BCryptPasswordEncoder {
    @Override
    protected String encodeNonNullPassword(String rawPassword) {
        return super.encodeNonNullPassword(DigestUtils.md5Hex(rawPassword));
    }

    @Override
    protected boolean matchesNonNull(String rawPassword, String encodedPassword) {
        return super.matchesNonNull(DigestUtils.md5Hex(rawPassword), encodedPassword);
    }
}
