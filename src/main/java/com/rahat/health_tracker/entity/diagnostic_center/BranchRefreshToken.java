package com.rahat.health_tracker.entity.diagnostic_center;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity(name = "branch_refresh_token")
@RequiredArgsConstructor
@AllArgsConstructor
public class BranchRefreshToken {

    @Id
    @SequenceGenerator(name = "branch_refresh_token_sequence", sequenceName = "branch_refresh_token_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_refresh_token_sequence")
    @Column(name = "id")
    private Long id;

//    @Column(nullable = false, unique = true)
    private String refreshToken;

    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}

