package com.rahat.health_tracker.entity.doctor.master;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "specialty")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Specialty {
    
    @Id
    @SequenceGenerator(name = "specialty_id_sequence", sequenceName = "specialty_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "specialty_id_sequence")
    @Column(name = "specialty_id")
    private Long Id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;
    // Orthopaedic Surgeon, Cardiologist
}

