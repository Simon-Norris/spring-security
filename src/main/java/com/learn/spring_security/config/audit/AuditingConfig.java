package com.learn.spring_security.config.audit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {

    @Bean
    @Qualifier("springSecurityAuditor")
    public AuditorAware<String> springSecurityAuditor() {
        return new SpringSecurityAuditorAware();
    }

    @Bean
    @Qualifier("systemAuditor")
    public AuditorAware<String> systemAuditor() {
        return new SystemAuditAwareImpl();
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return springSecurityAuditor();
    }
}
