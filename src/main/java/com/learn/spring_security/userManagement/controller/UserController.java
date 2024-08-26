package com.learn.spring_security.userManagement.controller;

import com.learn.spring_security.userManagement.entity.User;
import com.learn.spring_security.userManagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUsers(@RequestBody Map<String, String> req) {
        User user = User.builder()
                .username(req.get("email"))
                .firstname(req.get("firstname"))
                .lastname(req.get("lastname"))
                .password(req.get("password"))
                .build();

        User savedUser = userService.createUser(user);

        return ResponseEntity.ok(savedUser);
    }
}
