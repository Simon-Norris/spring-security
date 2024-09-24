package com.learn.spring_security.base.entity;

import com.learn.spring_security.base.userManagement.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant issuedDate = Instant.now();

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(columnDefinition = "TEXT")
    private String attributes;

    @Column(nullable = false,  columnDefinition = "TEXT")
    private String accessToken;

    @Version
    private int version;

}
