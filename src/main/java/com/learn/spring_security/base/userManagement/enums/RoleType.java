package com.learn.spring_security.base.userManagement.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    SUPER_ADMIN("SUPER_ADMIN", "Super admin"),
    ADMIN("ADMIN", "Admin"),
    EDITOR("EDITOR", "Editor"),
    USERS("USERS", "Users");

    private final String key;
    private final String value;
    RoleType(String value, String key) {
        this.value = value;
        this.key = key;
    }
}
