package com.rahat.health_tracker.entity.doctor;


import com.rahat.health_tracker.entity.User;
import com.rahat.health_tracker.entity.doctor.master.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity(name = "doctor_refresh_token")
@RequiredArgsConstructor
@AllArgsConstructor
public class DoctorRefreshToken {

    @Id
    @SequenceGenerator(name = "doctor_refresh_token_sequence", sequenceName = "doctor_refresh_token_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_refresh_token_sequence")
    @Column(name = "id")
    private Long id;

//    @Column(nullable = false, unique = true)
    private String refreshToken;

    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}

