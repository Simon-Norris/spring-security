package com.learn.spring_security.app.general.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailRequest {
    private String to;
    private String subject;
    private String text;
}
