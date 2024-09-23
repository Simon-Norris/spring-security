package com.learn.spring_security.app.auth.controller;

import com.learn.spring_security.app.auth.dto.AuthResponseDto;
import com.learn.spring_security.app.auth.dto.LoginRequestDto;
import com.learn.spring_security.app.auth.dto.UserRegisterDto;
import com.learn.spring_security.base.userManagement.entity.User;
import com.learn.spring_security.base.userManagement.enums.RoleType;
import com.learn.spring_security.base.userManagement.service.UserService;
import com.learn.spring_security.config.security.JwtUtils;
import com.learn.spring_security.utils.ApiResponse;
import com.learn.spring_security.utils.ApiResponseModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseModel> createUsers(@Valid @RequestBody UserRegisterDto req) {
        User user = User.builder()
                .username(req.getEmail())
                .firstname(req.getFirstname())
                .lastname(req.getLastname())
                .password(passwordEncoder.encode(req.getPassword()))
                .roles(Set.of(userService.createRole(RoleType.USERS)))
                .build();

        try {
            User savedUser = userService.createUser(user);
            return ApiResponse.success(HttpStatus.CREATED, "User created successfully", req.getEmail());
        } catch (Exception e) {
            return ApiResponse.badRequestWithReason("Failed to create user at the moment. Please try again later");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseModel> login(@RequestBody LoginRequestDto req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthResponseDto authResponseDto = AuthResponseDto.builder()
                .accessToken(jwtUtils.generateToken(req.getUsername()))
                .refreshToken(jwtUtils.generateRefreshToken(req.getUsername()))
                .username(req.getUsername())
                .build();
        return ApiResponse.success(HttpStatus.OK, "Token created successfully", authResponseDto);
    }
}
