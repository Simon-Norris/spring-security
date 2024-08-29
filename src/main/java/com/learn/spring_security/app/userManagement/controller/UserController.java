package com.learn.spring_security.app.userManagement.controller;

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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create")
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
}
