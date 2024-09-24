package com.learn.spring_security.app.auth.controller;

import com.learn.spring_security.app.auth.dto.AuthResponseDto;
import com.learn.spring_security.app.auth.dto.LoginRequestDto;
import com.learn.spring_security.app.auth.dto.RefreshTokenReqDto;
import com.learn.spring_security.app.auth.dto.UserRegisterDto;
import com.learn.spring_security.app.exceptions.TokenRefreshException;
import com.learn.spring_security.app.exceptions.UsernameNotFoundException;
import com.learn.spring_security.base.entity.RefreshToken;
import com.learn.spring_security.base.service.RefreshTokenService;
import com.learn.spring_security.base.userManagement.entity.User;
import com.learn.spring_security.base.userManagement.enums.RoleType;
import com.learn.spring_security.base.userManagement.service.UserService;
import com.learn.spring_security.config.security.JwtUtils;
import com.learn.spring_security.utils.ApiResponse;
import com.learn.spring_security.utils.ApiResponseModel;
import com.learn.spring_security.utils.EncryptionUtils;
import com.learn.spring_security.utils.UtilService;
import jakarta.servlet.http.HttpServletRequest;
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

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
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
    public ResponseEntity<ApiResponseModel> login(@RequestBody LoginRequestDto req, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateToken(req.getUsername());
        try {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(req.getUsername(), UtilService.refreshTokenAttributes(request), jwtToken);
            AuthResponseDto authResponseDto = AuthResponseDto.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken.getToken())
                    .username(req.getUsername())
                    .build();
            return ApiResponse.success(HttpStatus.OK, "Token created successfully", authResponseDto);
        } catch (UsernameNotFoundException | TokenRefreshException exception) {
            return ApiResponse.errorWithStatusAndMessage(HttpStatus.UNAUTHORIZED, "Token creation failed");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponseModel> refreshToken(@RequestBody RefreshTokenReqDto req, HttpServletRequest request) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByToken(req.getToken());
        if (refreshTokenOptional.isEmpty()) {
            return ApiResponse.errorWithStatusAndMessage(HttpStatus.UNAUTHORIZED, "No such token exists");
        }
        RefreshToken refreshToken = refreshTokenOptional.get();
        Optional<User> optionalUser = userService.findUserByName(refreshToken.getUser().getUsername());
        if (optionalUser.isEmpty()) return ApiResponse.errorWithStatusAndMessage(HttpStatus.UNAUTHORIZED, "User doesn't exist");
        User user = optionalUser.get();

        try {
            refreshTokenService.verifyExpiration(refreshToken, req.getAccessToken());
            String newAccessToken = jwtUtils.generateToken(user.getUsername());
            RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(refreshToken.getToken(), user.getUsername(), UtilService.refreshTokenAttributes(request), newAccessToken);

            AuthResponseDto authResponseDto = AuthResponseDto.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken.getToken())
                    .username(user.getUsername())
                    .build();
            return ApiResponse.success(HttpStatus.OK, "Token refreshed successfully", authResponseDto);
        } catch (TokenRefreshException e) {
            return ApiResponse.errorWithStatusAndMessage(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (UsernameNotFoundException e) {
            return ApiResponse.errorWithStatusAndMessage(HttpStatus.UNAUTHORIZED, "Token creation failed");
        }
    }
}
