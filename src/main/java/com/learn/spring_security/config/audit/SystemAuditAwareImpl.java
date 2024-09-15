package com.learn.spring_security.config.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SystemAuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("System");
    }
}
