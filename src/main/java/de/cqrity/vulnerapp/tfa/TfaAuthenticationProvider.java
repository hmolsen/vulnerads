package de.cqrity.vulnerapp.tfa;

import de.cqrity.vulnerapp.config.DatabaseEncryptor;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.tfa.authdetails.TfaWebAuthenticationDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TfaAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final Integer submittedTfaCode = ((TfaWebAuthenticationDetails) authentication.getDetails()).getTfaKey();
        final User user = (User) getUserDetailsService().loadUserByUsername(authentication.getName());

        if (user == null) {
            throw new BadCredentialsException("authentication failed");
        }

        if (user.isTfaEnabled()) {
            if (submittedTfaCode == null) {
                throw new BadCredentialsException("No Two-Factor Authentication Code provided");
            }

            byte[] encryptedTfaSecret = user.getEncryptedTfaSecret();
            final DatabaseEncryptor databaseEncryptor = DatabaseEncryptor.getInstance();
            final TfaAuthenticator tfaAuthenticator = new TfaAuthenticator(databaseEncryptor, encryptedTfaSecret, 2);

            if (!tfaAuthenticator.verifyCode(submittedTfaCode)) {
                System.out.printf("Code %d was not valid", submittedTfaCode);
                throw new BadCredentialsException("Invalid Two-Factor Authentication Code provided");
            }
        }

        final Authentication result = super.authenticate(authentication);
        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
