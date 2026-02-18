package com.rahat.health_tracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "daily_health_metrics",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "metric_date"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyHealthMetrics {

    @Id
    @SequenceGenerator(name = "disease_id_sequence", sequenceName = "disease_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disease_id_sequence")
    @Column(name = "disease_id")
    private Long id;
    // =================== Date ===================

    @Column(name = "metric_date", nullable = false)
    private LocalDate metricDate;

    // =================== Vital Signs ===================

    private Integer heartRate;        // bpm
    private Integer systolicBP;        // upper BP
    private Integer diastolicBP;       // lower BP
    private Double bodyTemperature;    // Celsius
    private Integer oxygenSaturation;  // SpO2 %

    // =================== Metabolic ===================

    private Double bloodSugarFasting;
    private Double bloodSugarPostMeal;

    private Double weightKg;

    // =================== Symptoms ===================

    @Column(length = 1000)
    private String symptoms; // headache, fever, nausea etc.

    @Column(length = 1000)
    private String notes;

    // =================== Relationships ===================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // =================== Audit ===================

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // =================== Lifecycle ===================

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
