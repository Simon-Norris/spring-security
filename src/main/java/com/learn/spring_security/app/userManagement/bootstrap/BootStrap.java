package com.learn.spring_security.app.userManagement.bootstrap;

import com.learn.spring_security.app.userManagement.entity.Role;
import com.learn.spring_security.app.userManagement.entity.User;
import com.learn.spring_security.app.userManagement.enums.RoleType;
import com.learn.spring_security.app.userManagement.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class BootStrap implements CommandLineRunner {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public BootStrap(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            this.initUsers();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void initUsers() {
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
    }
}
