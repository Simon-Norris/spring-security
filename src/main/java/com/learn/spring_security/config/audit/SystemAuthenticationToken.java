package com.learn.spring_security.config.audit;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class SystemAuthenticationToken implements Authentication {

    private final String systemAuditor;

    public SystemAuthenticationToken(String systemAuditor) {
        this.systemAuditor = systemAuditor;
    }

    @Override
    public String getName() {
        return systemAuditor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;  // No roles for system actions
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return systemAuditor;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // No action needed
    }
}
