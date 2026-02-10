package com.rahat.health_tracker.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity(name = "refresh_token")
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRefreshToken {

    @Id
    @SequenceGenerator(name = "refresh_token_sequence", sequenceName = "refresh_token_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_sequence")
    @Column(name = "id")
    private Long id;

//    @Column(nullable = false, unique = true)
    private String refreshToken;

    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

