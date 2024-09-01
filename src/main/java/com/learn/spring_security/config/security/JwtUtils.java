package com.learn.spring_security.config.security;

import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private static final long JWT_EXPIRATION = 1000 * 60 * 60; // 1 hour expiration

}
