package com.rahat.health_tracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chronic_disease")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChronicDisease {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME) // UUID v7
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(nullable = false, length = 100)
    private String diseaseName;   // Diabetes, Hypertension

    @Column(length = 1000)
    private String description;

    private LocalDate diagnosedDate;

    @Column(nullable = false)
    private Boolean active; // currently suffering or not

    // =================== Relationships ===================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "health_profile_id", nullable = false)
    private UserProfile userProfile;

    // =================== Audit ===================

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
