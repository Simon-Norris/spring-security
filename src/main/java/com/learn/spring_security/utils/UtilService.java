package com.learn.spring_security.utils;

import com.learn.spring_security.app.exceptions.UsernameNotFoundException;
import com.learn.spring_security.config.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UtilService {

    public static SecurityUser loggedInUser() throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof SecurityUser)
            return (SecurityUser) authentication.getPrincipal();
        else throw new UsernameNotFoundException("Logged in User not identified");
    }
}
