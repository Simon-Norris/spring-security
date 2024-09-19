package com.learn.spring_security.app.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserRegisterDto {

    private String email;
    private String firstname;
    private String lastname;
    private String password;
}
