package com.learn.spring_security.app.auth;

import com.learn.spring_security.app.userManagement.entity.User;
import com.learn.spring_security.app.userManagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/**")
public class AuthenticationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUsers(@RequestBody Map<String, String> req) {
        User user = User.builder()
                .username(req.get("email"))
                .firstname(req.get("firstname"))
                .lastname(req.get("lastname"))
                .password(passwordEncoder.encode(req.get("password")))
                .authority("ROLE_USER")
                .build();

        User savedUser = userService.createUser(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {

        return ResponseEntity.ok(req);
    }
}
