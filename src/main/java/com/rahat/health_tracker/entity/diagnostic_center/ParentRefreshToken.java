package com.rahat.health_tracker.entity.diagnostic_center;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity(name = "parent_refresh_token")
@RequiredArgsConstructor
@AllArgsConstructor
public class ParentRefreshToken {

    @Id
    @SequenceGenerator(name = "parent_refresh_token_sequence", sequenceName = "parent_refresh_token_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parent_refresh_token_sequence")
    @Column(name = "id")
    private Long id;

//    @Column(nullable = false, unique = true)
    private String refreshToken;

    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "parent_company_id")
    private ParentCompany parentCompany;
}

