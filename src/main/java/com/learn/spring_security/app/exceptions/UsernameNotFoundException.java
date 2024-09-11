package com.learn.spring_security.app.exceptions;

public class UsernameNotFoundException extends Exception{

    private final String message;
    private Throwable throwable;

    public UsernameNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public UsernameNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.throwable = throwable;
    }
}
