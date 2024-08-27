package com.learn.spring_security.userManagement.controller;

import com.learn.spring_security.userManagement.entity.User;
import com.learn.spring_security.userManagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
