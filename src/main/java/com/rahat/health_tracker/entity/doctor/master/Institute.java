package com.rahat.health_tracker.entity.doctor.master;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "institute")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Institute {

    @Id
    @SequenceGenerator(name = "institute_id_sequence", sequenceName = "institute_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "institute_id_sequence")
    @Column(name = "institute_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 200)
    private String name;

    @Column(nullable = false, unique = true, length = 200)
    private String shortName;
}
