package com.rahat.health_tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BranchResponseDto {
    private Long id;
    private String email;
    private String phoneNo;
    private String branchAddress;
    private Double latitude;
    private Double longitude;
    private String branchName;
}
