package com.rahat.health_tracker.utils;

import com.rahat.health_tracker.entity.User;
import com.rahat.health_tracker.entity.diagnostic_center.Branch;
import com.rahat.health_tracker.entity.diagnostic_center.ParentCompany;
import com.rahat.health_tracker.entity.doctor.master.Doctor;
import com.rahat.health_tracker.enums.Role;
import com.rahat.health_tracker.repository.BranchRepository;
import com.rahat.health_tracker.repository.DoctorRepository;
import com.rahat.health_tracker.repository.ParentCompanyRepository;
import com.rahat.health_tracker.repository.UserRepository;
import com.rahat.health_tracker.security.AuthPrincipal;
import com.rahat.health_tracker.security.SecurityUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.print.Doc;

@Component
@RequiredArgsConstructor
public class SecurityContextResolver {

    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final ParentCompanyRepository parentCompanyRepository;
    private final DoctorRepository doctorRepository;

    private static UserRepository USER_REPO;
    private static BranchRepository BRANCH_REPO;
    private static ParentCompanyRepository PARENT_REPO;
    private static DoctorRepository DOCTOR_REPO;

    @PostConstruct
    public void init() {
        USER_REPO = userRepository;
        BRANCH_REPO = branchRepository;
        PARENT_REPO = parentCompanyRepository;
        DOCTOR_REPO = doctorRepository;
    }

    public static User getCurrentUser() {
        AuthPrincipal principal = SecurityUtils.current();

        if (principal.getRole() != Role.USER) {
            throw new AccessDeniedException("Not a user");
        }

        return USER_REPO.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public static Branch getCurrentBranch() {
        AuthPrincipal principal = SecurityUtils.current();

        if (principal.getRole() != Role.BRANCH_ADMIN) {
            throw new AccessDeniedException("Not a branch admin");
        }

        return BRANCH_REPO.findByEmailAndIsEnabled(principal.getUsername(), true)
                .orElseThrow(() -> new IllegalStateException("Branch not found"));
    }

    public static ParentCompany getCurrentParent() {
        AuthPrincipal principal = SecurityUtils.current();

        if (principal.getRole() != Role.COMPANY_ADMIN) {
            throw new AccessDeniedException("Not a parent");
        }

        return PARENT_REPO.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Parent company not found"));
    }

    public static Doctor getCurrentDoctor() {
        AuthPrincipal principal = SecurityUtils.current();

        if (principal.getRole() != Role.DOCTOR) {
            throw new AccessDeniedException("Not a doctor");
        }

        return DOCTOR_REPO.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Doctor not found"));
    }
}
