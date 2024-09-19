package com.learn.spring_security.app.auth.dto;

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
