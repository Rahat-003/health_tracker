package com.rahat.health_tracker.entity.diagnostic_center;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;


@Entity
@Table(
    name = "parent_company",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "company_name"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentCompany {

    @Id
    @SequenceGenerator(name = "parent_company_id_sequence", sequenceName = "parent_company_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parent_company_id_sequence")
    @Column(name = "parent_company_id")
    private Long Id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "password")
    private String password;

    @Column(name = "is_enabled")
    private Boolean isEnabled;
}
