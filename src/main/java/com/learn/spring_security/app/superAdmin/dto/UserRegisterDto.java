package com.learn.spring_security.app.superAdmin.dto;

import com.learn.spring_security.base.userManagement.enums.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UserRegisterDto {

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "First name is required")
    private String firstname;

    @NotNull(message = "Last name is required")
    private String lastname;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "At least one role should be selected")
    private Set<RoleType> selectedRoleTypes;
}
