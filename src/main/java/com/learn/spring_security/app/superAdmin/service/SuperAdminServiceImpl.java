package com.learn.spring_security.app.superAdmin.service;

import com.learn.spring_security.app.superAdmin.dto.UserRegisterDto;
import com.learn.spring_security.base.userManagement.entity.Role;
import com.learn.spring_security.base.userManagement.entity.User;
import com.learn.spring_security.base.userManagement.service.UserService;
import com.learn.spring_security.utils.ApiResponse;
import com.learn.spring_security.utils.ApiResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminServiceImpl implements SuperAdminService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SuperAdminServiceImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponseModel> createUser(UserRegisterDto req) {

        if (req.getSelectedRoleTypes().isEmpty()) return ApiResponse.errorWithStatusAndMessage(HttpStatus.BAD_REQUEST, "Role type is required");
        Optional<User> userOptional = this.userService.findUserByName(req.getEmail());
        if (userOptional.isPresent()) return ApiResponse.errorWithStatusAndMessage(HttpStatus.ALREADY_REPORTED, "User already exists");
        try {
            Set<Role> roles = req.getSelectedRoleTypes().stream().map(userService::createRole).collect(Collectors.toSet());
            User user = User.builder()
                    .username(req.getEmail())
                    .firstname(req.getFirstname())
                    .lastname(req.getLastname())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .roles(roles)
                    .build();
            userService.createUser(user);
            return ApiResponse.success(HttpStatus.CREATED, "User created successfully", req.getEmail());
        } catch (Exception e) {
            return ApiResponse.success(HttpStatus.CREATED, "Failed to create user at the moment. Please try again later", req.getEmail());
        }
    }

}
