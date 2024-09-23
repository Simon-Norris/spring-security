package com.learn.spring_security.app.exceptions;

import lombok.Getter;

@Getter
public class TokenRefreshException extends Exception {

    private final String message;
    private Throwable throwable;

    public TokenRefreshException(String message) {
        super(message);
        this.message = message;
    }

    public TokenRefreshException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.throwable = throwable;
    }
}
