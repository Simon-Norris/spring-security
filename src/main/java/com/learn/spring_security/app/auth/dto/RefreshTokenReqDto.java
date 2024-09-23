package com.learn.spring_security.app.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RefreshTokenReqDto {

    private String token;
    private String accessToken;
}
