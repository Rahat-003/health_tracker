package com.rahat.health_tracker.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME) // UUID v7 / time-ordered
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column
    private Boolean smoker;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // =================== Relationships ===================

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private HealthProfile healthProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Activity> activities = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<NutritionLog> nutritionLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DailyHealthMetrics> dailyHealthMetrics = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Medication> medications = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DoctorVisit> doctorVisits = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SymptomLog> symptomLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DiagnosticReport> diagnosticReports = new ArrayList<>();

    // =================== Helper Methods ===================

    public void addActivity(Activity activity) {
        activities.add(activity);
        activity.setUser(this);
    }

//    public void addNutritionLog(NutritionLog log) {
//        nutritionLogs.add(log);
//        log.setUser(this);
//    }

    public void addDailyHealthMetric(DailyHealthMetrics metric) {
        dailyHealthMetrics.add(metric);
        metric.setUser(this);
    }

    public void setHealthProfile(HealthProfile profile) {
        this.healthProfile = profile;
        profile.setUser(this);
    }

    public void addMedication(Medication medication) {
        medications.add(medication);
        medication.setUser(this);
    }

    public void addDoctorVisit(DoctorVisit visit) {
        doctorVisits.add(visit);
        visit.setUser(this);
    }

    public void addSymptomLog(SymptomLog log) {
        symptomLogs.add(log);
        log.setUser(this);
    }

    public void addDiagnosticReport(DiagnosticReport report) {
        diagnosticReports.add(report);
        report.setUser(this);
    }

    // =================== Lifecycle Hooks ===================

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
