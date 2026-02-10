package com.rahat.health_tracker.entity.diagnostic_center;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(
    name = "branch",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone_no")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Id
    @SequenceGenerator(name = "branch_id_sequence", sequenceName = "branch_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_id_sequence")
    @Column(name = "branch_id")
    private Long id;

    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @ManyToOne
    @JoinColumn(name = "parent_company_id")
    private ParentCompany parentCompany;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_no", unique = true, nullable = false)
    private String phoneNo;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    private Double latitude;

    private Double longitude;

    @Column(name = "branch_address", nullable = false)
    private String branchAddress;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "branch")
    private Set<Room> rooms;

}
