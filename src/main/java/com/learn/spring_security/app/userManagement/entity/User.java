package com.learn.spring_security.app.userManagement.entity;

import com.learn.spring_security.base.entity.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User extends Auditable {

    @Column(unique = true, nullable = false)
    private String username;

    private String firstname;
    private String lastname;

    private String password;

    @Column(nullable = false)
    private String authority;

    private boolean enabled;

    private boolean locked;

    private boolean expired;

    private boolean credentialsExpired;
}
