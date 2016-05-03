package de.cqrity.vulnerapp.tfa;

import de.cqrity.vulnerapp.config.DatabaseEncryptor;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.tfa.authdetails.TfaWebAuthenticationDetails;
import de.cqrity.vulnerapp.tfa.exception.MissingTfaKeyAuthenticatorException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TfaAuthenticationProvider extends DaoAuthenticationProvider {

    private TfaAuthenticator tfaAuthenticator;

    private DatabaseEncryptor databaseEncryptor;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        if (authentication.getDetails() instanceof TfaWebAuthenticationDetails) {
            User user = (User) userDetails;
            if (user.isTfaEnabled()) {
                byte[] encryptedTfaSecret = user.getEncryptedTfaSecret();
                Integer tfaKey = ((TfaWebAuthenticationDetails) authentication.getDetails()).getTfaKey();
                if (tfaKey != null) {
                    try {
                        if (!tfaAuthenticator.verifyCode(databaseEncryptor.decrypt(encryptedTfaSecret), tfaKey, 2)) {
                            System.out.printf("Code %d was not valid", tfaKey);
                            authentication.eraseCredentials();
                            throw new BadCredentialsException("Invalid 2FA code");
                        }
                    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                        authentication.eraseCredentials();
                        throw new InternalAuthenticationServiceException("2FA code verification failed", e);
                    }
                } else {
                    authentication.eraseCredentials();
                    throw new MissingTfaKeyAuthenticatorException("2FA code is mandatory");
                }
            }
        }
    }

    public void setTfaAuthenticator(TfaAuthenticator tfaAuthenticator) {
        this.tfaAuthenticator = tfaAuthenticator;
    }

    public void setDatabaseEncryptor(DatabaseEncryptor databaseEncryptor) {
        this.databaseEncryptor = databaseEncryptor;
    }
}
