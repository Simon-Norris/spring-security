package com.learn.spring_security.app.exceptions;

import lombok.Getter;

@Getter
public class RoleNotFoundException extends Exception {

    private final String message;
    private Throwable throwable;

    public RoleNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public RoleNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.throwable = throwable;
    }
}
