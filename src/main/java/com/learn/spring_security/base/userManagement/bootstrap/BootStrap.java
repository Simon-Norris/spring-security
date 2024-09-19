package com.learn.spring_security.base.userManagement.bootstrap;

import com.learn.spring_security.base.userManagement.entity.Role;
import com.learn.spring_security.base.userManagement.entity.User;
import com.learn.spring_security.base.userManagement.enums.RoleType;
import com.learn.spring_security.base.userManagement.service.UserService;
import com.learn.spring_security.config.audit.SystemAuthenticationToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Component
public class BootStrap implements CommandLineRunner {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuditorAware<String> systemAuditorAware;


    public BootStrap(UserService userService, PasswordEncoder passwordEncoder, @Qualifier("systemAuditor") AuditorAware<String> systemAuditorAware) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.systemAuditorAware = systemAuditorAware;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {
            this.initUsers();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void initUsers() {
        Optional<String> systemAuditor = this.systemAuditorAware.getCurrentAuditor();
        systemAuditor.ifPresent(auditor -> {
            SecurityContextHolder.getContext().setAuthentication(new SystemAuthenticationToken(auditor));
            Role superAdminRole = userService.createRole(RoleType.SUPER_ADMIN);
            User user = User.builder()
                    .username("simon1.neupane@gmail.com")
                    .firstname("Simon")
                    .lastname("Neupane")
                    .password(passwordEncoder.encode("simon2024@XFactor"))
                    .roles(Set.of(superAdminRole))
                    .enabled(true)
                    .credentialsExpired(false)
                    .locked(false)
                    .expired(false)
                    .build();
            userService.createUser(user);
        });
    }
}
