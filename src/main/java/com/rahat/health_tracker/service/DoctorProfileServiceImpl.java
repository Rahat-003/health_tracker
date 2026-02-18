package com.rahat.health_tracker.service;

import com.rahat.health_tracker.dto.request.ApplyChamberRequestDto;
import com.rahat.health_tracker.dto.request.DoctorProfileRequestDto;
import com.rahat.health_tracker.dto.response.ChamberResponseDto;
import com.rahat.health_tracker.dto.response.DoctorInfo;
import com.rahat.health_tracker.dto.response.DoctorProfileResponseDto;
import com.rahat.health_tracker.entity.diagnostic_center.Branch;
import com.rahat.health_tracker.entity.diagnostic_center.Chamber;
import com.rahat.health_tracker.entity.doctor.DoctorChamberApply;
import com.rahat.health_tracker.entity.doctor.DoctorProfile;
import com.rahat.health_tracker.entity.doctor.master.*;
import com.rahat.health_tracker.enums.DoctorOffDay;
import com.rahat.health_tracker.exception.DoctorProfileNotFoundException;
import com.rahat.health_tracker.mapper.DoctorInfoMapper;
import com.rahat.health_tracker.repository.*;
import com.rahat.health_tracker.utils.SecurityContextResolver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorProfileServiceImpl implements DoctorProfileService {

    private final DepartmentRepository departmentRepository;
    private final InstituteRepository instituteRepository;
    private final DesignationRepository designationRepository;
    private final SpecialtyRepository specialtyRepository;
    private final QualificationRepository qualificationRepository;
    private final DoctorProfileRepository doctorProfileRepository;
    private final BranchRepository branchRepository;
    private final DoctorChamberApplyRepository doctorChamberApplyRepository;
    private final ChamberRepository chamberRepository;

    @Override
    public DoctorInfo getInfo() {
        return DoctorInfoMapper.INSTANCE.toDoctorInfo(
                departmentRepository.findAll(),
                designationRepository.findAll(),
                instituteRepository.findAll(),
                specialtyRepository.findAll(),
                qualificationRepository.findAll()
        );
    }

    @Override
    @Transactional
    public DoctorProfileResponseDto createProfile(DoctorProfileRequestDto dto) {
        Doctor doctor = SecurityContextResolver.getCurrentDoctor();

        DoctorProfile doctorProfile = (DoctorProfile) doctorProfileRepository.findByDoctor(doctor)
                .map(existing -> {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Profile already created"
                    );
                })
                .orElseGet(() -> DoctorProfile.builder()
                        .doctor(doctor)
                        .createdAt(LocalDateTime.now())
//                        .institutes(new HashSet<>())
//                        .specialties(new HashSet<>())
//                        .qualifications(new HashSet<>())
                        .build());

        if (dto.getDesignationId() != null) {
            Designation designation = designationRepository.findById(dto.getDesignationId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Invalid designation id"));
            doctorProfile.setDesignation(designation);
        }


        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Invalid department id"));
            doctorProfile.setDepartment(department);
        }

        // ---- Collections (ManyToMany) ----


        log.info("instituteIds: "  + dto.getInstituteIds());
        List<Institute> instituteList =
                instituteRepository.findAllById(dto.getInstituteIds());

        log.info("hello: " + instituteList.getFirst().getName());


        replaceCollection(
                doctorProfile.getInstitutes(),
                instituteRepository.findAllById(dto.getInstituteIds())
        );

            System.out.println("Hello createProfile");


        replaceCollection(
                doctorProfile.getSpecialties(),
                specialtyRepository.findAllById(dto.getSpecialtyIds())
        );

        replaceCollection(
                doctorProfile.getQualifications(),
                qualificationRepository.findAllById(dto.getQualificationIds())
        );

        doctorProfile.setUpdatedAt(LocalDateTime.now());

        DoctorProfile saved = doctorProfileRepository.save(doctorProfile);

        return mapToResponseDto(saved, doctor);
    }

    private DoctorProfileResponseDto mapToResponseDto(DoctorProfile profile, Doctor doctor) {
        return DoctorProfileResponseDto.builder()
                .doctorName(doctor.getName())
                .designation(profile.getDesignation() != null ? profile.getDesignation().getTitle() : null)
                .department(profile.getDepartment() != null ? profile.getDepartment().getName() : null)
                .institutes(profile.getInstitutes().stream()
                        .map(Institute::getName)
                        .collect(Collectors.toList()))
                .specialties(profile.getSpecialties().stream()
                        .map(Specialty::getName)
                        .collect(Collectors.toList()))
                .qualifications(profile.getQualifications().stream()
                        .map(Qualification::getName)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public DoctorProfile updateProfile(DoctorProfileRequestDto dto) {
        Doctor doctor = SecurityContextResolver.getCurrentDoctor();

        DoctorProfile doctorProfile = doctorProfileRepository
                .findByDoctor(doctor)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor Profile is not created"));

        if (dto.getDesignationId() != null) {
            Designation designation = designationRepository.findById(dto.getDesignationId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Invalid designation id"));
            doctorProfile.setDesignation(designation);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Invalid department id"));
            doctorProfile.setDepartment(department);
        }

        // ---- Collections (ManyToMany) ----
        replaceCollection(
                doctorProfile.getInstitutes(),
                instituteRepository.findAllById(dto.getInstituteIds())
        );

        replaceCollection(
                doctorProfile.getSpecialties(),
                specialtyRepository.findAllById(dto.getSpecialtyIds())
        );

        replaceCollection(
                doctorProfile.getQualifications(),
                qualificationRepository.findAllById(dto.getQualificationIds())
        );

        doctorProfile.setUpdatedAt(LocalDateTime.now());

        return doctorProfileRepository.save(doctorProfile);
    }

    private <T> void replaceCollection(Set<T> target, Iterable<T> source) {
        target.clear();
        if (source != null) {
            source.forEach(target::add);
        }
    }

    @Override
    public DoctorProfileResponseDto getDoctorProfile() {

        Doctor doctor = SecurityContextResolver.getCurrentDoctor();
        log.info("doctor name: " + doctor.getName());

        DoctorProfile doctorProfile = doctorProfileRepository.findByDoctor(doctor)
                .orElseThrow(DoctorProfileNotFoundException::new);


        List<String> institutes = Optional.ofNullable(doctorProfile.getInstitutes())
                .orElse(Set.of())           // empty set if null
                .stream()
                .map(Institute::getShortName)
                .toList();

        List<String> specialities = Optional.ofNullable(doctorProfile.getSpecialties())
                .orElse(Set.of())
                .stream()
                .map(Specialty::getName)
                .toList();

        List<String> qualifications = Optional.ofNullable(doctorProfile.getQualifications())
                .orElse(Set.of())
                .stream()
                .map(Qualification::getName)
                .toList();

        List<ChamberResponseDto> chambers = new ArrayList<>();
        List<Chamber> chamberList = chamberRepository.findByDoctor_DoctorIdAndIsActive(doctor.getDoctorId(), true);
        chamberList.forEach(item -> {
            chambers.add(mapToChamberResponse(item));
        });

        return DoctorProfileResponseDto.builder()
//                .doctorId(doctorProfile.getDoctor().getDoctorId())
                .doctorName(doctorProfile.getDoctor().getName())
                .designation(doctorProfile.getDesignation().getTitle())
                .department(doctorProfile.getDepartment().getName())
                .institutes(institutes)
                .specialties(specialities)
                .qualifications(qualifications)
                .chambers(chambers)
                .build();
    }

    private ChamberResponseDto mapToChamberResponse(Chamber chamber) {

        Branch branch = chamber.getBranch();

        return ChamberResponseDto.builder()
                .chamberId(chamber.getId())

                // Branch
                .branchName(branch.getBranchName())
                .latitude(branch.getLatitude())
                .longitude(branch.getLongitude())
                .branchAddress(branch.getBranchAddress())

                // Room
                .roomNo(chamber.getRoomNo())
                .floorNo(chamber.getFloorNo())

                // Chamber
                .startTime(chamber.getStartTime())
                .endTime(chamber.getEndTime())
                .offDays(String.valueOf(chamber.getDoctorOffDay()))

                .build();
    }

    @Override
    public Boolean applyChamber(ApplyChamberRequestDto request) {

        Doctor doctor = SecurityContextResolver.getCurrentDoctor();

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid branch id"));

        DoctorChamberApply doctorChamberApply = doctorChamberApplyRepository.findByDoctorIdAndBranchIdAndIsAccepted(doctor, branch, false);

        if (doctorChamberApply!=null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Doctor already applied for the chamber at this branch");
        }

        EnumSet<DoctorOffDay> offDays = request.getOffDayList() == null
                ? EnumSet.noneOf(DoctorOffDay.class)
                : EnumSet.copyOf(request.getOffDayList());

        DoctorChamberApply chamberApply = DoctorChamberApply.builder()
                .doctorId(doctor)
                .branchId(branch)
                .doctorOffDay(offDays)
                .visitingFee(request.getVisitingFee())
                .isActive(false)
                .isAccepted(false)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .createdAt(LocalDateTime.now())
                .build();

        doctorChamberApplyRepository.save(chamberApply);

        return true;
    }


}
