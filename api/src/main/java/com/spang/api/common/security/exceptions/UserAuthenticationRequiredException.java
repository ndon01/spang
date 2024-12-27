package com.spang.api.common.security.exceptions;

public class UserAuthenticationRequiredException extends RuntimeException {
    public UserAuthenticationRequiredException() {
        super("User authentication required.");
    }

    public UserAuthenticationRequiredException(String message) {
        super(message);
    }
}
