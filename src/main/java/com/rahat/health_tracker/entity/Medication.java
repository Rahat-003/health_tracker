package com.rahat.health_tracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "medication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medication {

    @Id
    @SequenceGenerator(name = "medication_id_sequence", sequenceName = "medication_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_id_sequence")
    @Column(name = "medication_id")
    private Long medicationId;

    @ManyToOne
    @JoinColumn(name = "medicine_name", nullable = false)
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dosage_id", nullable = false)
    private Dosage dosage;

    @Column(length = 100)
    private String frequency; // twice a day, before sleep

    private LocalDate startDate;
    private LocalDate endDate;

    private Boolean prescribedByDoctor;

    @Column(length = 1000)
    private String notes;

    // =================== Relationships ===================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_id")
    private Disease disease;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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
