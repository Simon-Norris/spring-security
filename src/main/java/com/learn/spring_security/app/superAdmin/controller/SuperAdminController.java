package com.learn.spring_security.app.superAdmin.controller;

import com.learn.spring_security.app.superAdmin.dto.UserRegisterDto;
import com.learn.spring_security.app.superAdmin.service.SuperAdminService;
import com.learn.spring_security.base.userManagement.entity.Role;
import com.learn.spring_security.base.userManagement.entity.User;
import com.learn.spring_security.base.userManagement.enums.RoleType;
import com.learn.spring_security.base.userManagement.service.UserService;
import com.learn.spring_security.utils.ApiResponse;
import com.learn.spring_security.utils.ApiResponseModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin-control")
@PreAuthorize(value = "hasRole('SUPER_ADMIN')")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }
    @PostMapping("/create-users")
    public ResponseEntity<ApiResponseModel> createUsers(@Valid @RequestBody UserRegisterDto req) {
        return superAdminService.createUser(req);
    }
}
