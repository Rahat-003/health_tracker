package com.rahat.health_tracker.entity.doctor.master;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "designation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Designation {

    @Id
    @SequenceGenerator(name = "designation_id_sequence", sequenceName = "designation_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "designation_id_sequence")
    @Column(name = "designation_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String title;
    // Assistant Professor, Professor
}
