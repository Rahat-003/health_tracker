package com.rahat.health_tracker.entity.doctor;

import com.rahat.health_tracker.entity.diagnostic_center.Chamber;
import com.rahat.health_tracker.entity.doctor.master.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(
    name = "doctor_profile",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "doctor_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorProfile {

    @Id
    @SequenceGenerator(name = "doctor_profile_id_sequence", sequenceName = "doctor_profile_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_profile_id_sequence")
    @Column(name = "doctor_profile_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false, unique = true)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designation_id")
    private Designation designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_profile_institute",
            joinColumns = @JoinColumn(name = "doctor_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "institute_id")
    )
    @Builder.Default
    private Set<Institute> institutes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_profile_specialty",
            joinColumns = @JoinColumn(name = "doctor_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    @Builder.Default
    private Set<Specialty> specialties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_profile_qualification",
            joinColumns = @JoinColumn(name = "doctor_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "qualification_id")
    )
    @Builder.Default
    private Set<Qualification> qualifications = new HashSet<>();

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "doctor_profile_chamber",
//            joinColumns = @JoinColumn(name = "doctor_profile_id"),
//            inverseJoinColumns = @JoinColumn(name = "chamber_id")
//    )
//    private Set<Chamber> chambers = new HashSet<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
