package com.rahat.health_tracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @SequenceGenerator(name = "user_profile_id_sequence", sequenceName = "user_profile_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_id_sequence")
    @Column(name = "user_profile_id")
    private Long userProfileId;


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private Boolean smoker;
    private Boolean alcoholic;

    private Double heightCm;
    private Double weightKg;

    @Column(length = 1000)
    private String allergies;

    @Column(length = 1000)
    private String familyMedicalHistory;

    @ManyToMany
    @JoinTable(
            name = "user_profile_father_side_disease",
            joinColumns = @JoinColumn(name = "user_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    private Set<Disease> familyDiseaseFromFatherSide = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "user_profile_mother_side_disease",
            joinColumns = @JoinColumn(name = "user_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    private Set<Disease> familyDiseaseFromMotherSide = new HashSet<>();


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
