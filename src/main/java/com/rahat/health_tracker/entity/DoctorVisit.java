package com.rahat.health_tracker.entity;


import com.rahat.health_tracker.entity.doctor.master.Doctor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "doctor_visit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorVisit {

    @Id
    @SequenceGenerator(name = "doctor_visit_id_sequence", sequenceName = "doctor_visit_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_visit_id_sequence")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_id")
    private Disease disease;

    private LocalDate visitDate;

    @Column(length = 1000)
    private String symptoms;

    @Column(length = 1000)
    private String diagnosisNotes;

    private Double consultationFee;
}
