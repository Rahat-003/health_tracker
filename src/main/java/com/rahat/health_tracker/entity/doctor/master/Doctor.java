package com.rahat.health_tracker.entity.doctor.master;



import com.rahat.health_tracker.entity.doctor.DoctorProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
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
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(nullable = false, length = 200)
    private String fullName; // DR. KAZI HANNANUR RAHMAN (JEWEL)

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL, optional = false)
    private DoctorProfile profile;

    private LocalDate createdAt;

    private LocalDate updatedAt;

}