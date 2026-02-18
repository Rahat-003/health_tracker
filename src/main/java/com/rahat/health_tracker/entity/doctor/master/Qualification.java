package com.rahat.health_tracker.entity.doctor.master;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "qualification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Qualification {

    @Id
    @SequenceGenerator(name = "qualification_id_sequence", sequenceName = "qualification_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qualification_id_sequence")
    @Column(name = "qualification_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;
    // MBBS, FCPS, MS, MPH, BCS
}
