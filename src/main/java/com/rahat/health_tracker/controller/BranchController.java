package com.rahat.health_tracker.controller;


import com.rahat.health_tracker.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/branch")
public class BranchController {

    private final BranchService branchService;

//    @PostMapping("/add-room")
//    public ResponseEntity<Boolean> addRoomToBranch(List<RoomRequestDto> roomRequestDtoList) {
//        return ResponseEntity.ok(branchService.addRoomToBranch(roomRequestDtoList));
//    }

//    @GetMapping("/applied-chamber-list")
//    public ResponseEntity<List<Doc>>

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/accept-chamber")
    public ResponseEntity<?> acceptChamberByRequest(
            @RequestParam("doctorId") String doctorId,
            @RequestParam("roomNo") String roomNo,
            @RequestParam("floorNo") String floorNo
        ) {
        Boolean result = branchService.acceptChamber(doctorId, roomNo, floorNo);
        return ResponseEntity.ok(true);
    }


}
