package com.learn.spring_security.app.userManagement.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;

}
