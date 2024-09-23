package com.learn.spring_security.app.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthResponseDto {

    private String accessToken;
    private String refreshToken;
    private String username;
}
