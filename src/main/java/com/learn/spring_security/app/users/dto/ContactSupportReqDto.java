package com.learn.spring_security.app.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ContactSupportReqDto {

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Full name is required")
    @NotBlank(message = "Full name cannot be blank")
    private String fullName;

    @NotNull(message = "Message is required")
    @NotBlank(message = "Message cannot be blank")
    private String message;

    private boolean internalIssue;
}
