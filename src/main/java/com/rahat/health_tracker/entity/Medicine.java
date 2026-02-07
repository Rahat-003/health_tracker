package com.rahat.health_tracker.entity;

import com.rahat.health_tracker.enums.MedicineType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "medicine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {


    @Id
    @SequenceGenerator(name = "medicine_id_sequence", sequenceName = "medicine_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicine_id_sequence")
    @Column(name = "medicine_id")
    private Long medicineId;


    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(length = 100)
    private String strength;  // 500mg, 10ml

    @Enumerated(EnumType.STRING)  // store enum as string in DB
    @Column(length = 50, nullable = false)
    private MedicineType type;

    @Column(length = 1000)
    private String description;

    private Integer quantity; // number of units available

    private Double price; // price per unit

}
