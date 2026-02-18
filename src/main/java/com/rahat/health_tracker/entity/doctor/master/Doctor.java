package com.rahat.health_tracker.entity.doctor.master;



import com.rahat.health_tracker.entity.doctor.DoctorProfile;
import com.rahat.health_tracker.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "doctor_id")
    private String doctorId;

    @Column(nullable = false, length = 200)
    private String name; // DR. KAZI HANNANUR RAHMAN (JEWEL)

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL, optional = false)
//    private DoctorProfile profile;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}