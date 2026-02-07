package com.rahat.health_tracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_disease_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDiseaseHistory {


    @Id
    @SequenceGenerator(name = "user_disease_hist_id_sequence", sequenceName = "user_disease_hist_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_disease_hist_id_sequence")
    @Column(name = "id")
    private Long Id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "disease_id", nullable = false)
    private Disease disease;

    private LocalDate diagnosedDate;
    private LocalDate recoveredDate;

    @Column(nullable = false)
    private Boolean active;
}
