package com.rahat.health_tracker.service;



public interface BranchService {
    Boolean acceptChamber(String doctorId, String roomNo, String floorNo);
//    Boolean addRoomToBranch(List<RoomRequestDto> roomRequestDtoList);
}
