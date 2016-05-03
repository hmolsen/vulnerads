package de.cqrity.vulnerapp.tfa.exception;

import org.springframework.security.core.AuthenticationException;

public class MissingTfaKeyAuthenticatorException extends AuthenticationException {
    public MissingTfaKeyAuthenticatorException(String message) {
        super(message);
    }
}
