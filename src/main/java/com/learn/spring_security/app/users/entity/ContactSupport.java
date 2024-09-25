package com.learn.spring_security.app.users.entity;

import com.learn.spring_security.app.users.enums.ContactSupportStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contact_support")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ContactSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String email;

    @Column(nullable = false, updatable = false)
    private String fullName;

    @Column(nullable = false,  columnDefinition = "TEXT", updatable = false)
    private String issue;

    private Long resolvedByUser;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactSupportStatus status;

    private String reviewMessage;

    @Column(nullable = false, updatable = false)
    private boolean internalIssue;

    @Version
    private int version;
}
