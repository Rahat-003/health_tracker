package com.rahat.health_tracker.service;


import com.rahat.health_tracker.entity.diagnostic_center.Branch;
import com.rahat.health_tracker.entity.diagnostic_center.Chamber;
import com.rahat.health_tracker.entity.doctor.DoctorChamberApply;
import com.rahat.health_tracker.entity.doctor.master.Doctor;
import com.rahat.health_tracker.repository.BranchRepository;
import com.rahat.health_tracker.repository.ChamberRepository;
import com.rahat.health_tracker.repository.DoctorChamberApplyRepository;
import com.rahat.health_tracker.repository.DoctorRepository;
import com.rahat.health_tracker.utils.SecurityContextResolver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService{


    private final BranchRepository branchRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorChamberApplyRepository doctorChamberApplyRepository;
    private final ChamberRepository chamberRepository;

//    @Override
//    @Transactional
//    public Boolean addRoomToBranch(List<RoomRequestDto> roomRequestDtoList) {
//        Branch branch = SecurityContextResolver.getCurrentBranch();
//        List<Room> roomList = new ArrayList<>();
//        roomRequestDtoList.forEach(item -> {
//            Room room = Room.builder()
//                    .branch(branch)
//                    .roomNo(item.getRoomNo())
//                    .floorNo(item.getFloorNo())
//                    .isActive(true)
//                    .createdAt(LocalDateTime.now())
//                    .updatedAt(LocalDateTime.now())
//                    .build();
//            roomList.add(room);
//        });
//
//        roomRepository.saveAll(roomList);
//        return true;
//    }

    @Override
    public Boolean acceptChamber(String doctorId, String roomNo, String floorNo) {

        Branch branch = SecurityContextResolver.getCurrentBranch();

        Doctor doctor = doctorRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));

        log.info("acceptChamber: {}", doctor.getName());

        DoctorChamberApply doctorChamberApply = doctorChamberApplyRepository.findByDoctorIdAndBranchIdAndIsAccepted(doctor, branch, false);

        if (doctorChamberApply == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not applied to this branch");
        }

        Optional<Chamber> exist = chamberRepository.findByDoctorAndBranch(doctor, branch);

        if (exist.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Chamber already exists for this doctor at the selected branch");
        }

        Chamber chamber = Chamber.builder()
                .doctor(doctor)
                .branch(branch)
                .roomNo(roomNo)
                .floorNo(floorNo)
                .startTime(doctorChamberApply.getStartTime())
                .endTime(doctorChamberApply.getEndTime())
                .isActive(true)
                .visitingFee(doctorChamberApply.getVisitingFee())
                .doctorOffDay(doctorChamberApply.getDoctorOffDay())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        chamberRepository.save(chamber);

        return true;
    }
}
