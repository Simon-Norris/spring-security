package com.learn.spring_security.userManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends Auditable {

    @Column(unique = true, nullable = false)
    private String username;

    private String firstname;
    private String lastname;

    private String password;
}
