package de.cqrity.vulnerapp.tfa;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MD5BCryptPasswordEncoder extends BCryptPasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(DigestUtils.md5Hex(rawPassword.toString()));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return super.matches(DigestUtils.md5Hex(rawPassword.toString()), encodedPassword);
    }
}
