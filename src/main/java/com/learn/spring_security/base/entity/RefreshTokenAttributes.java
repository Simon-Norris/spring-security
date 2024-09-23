package com.learn.spring_security.base.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RefreshTokenAttributes {

    private String device;
    private String ip;
}
