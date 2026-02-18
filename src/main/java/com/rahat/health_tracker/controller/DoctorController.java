package com.rahat.health_tracker.controller;


import com.rahat.health_tracker.dto.request.ApplyChamberRequestDto;
import com.rahat.health_tracker.dto.request.DoctorProfileRequestDto;
import com.rahat.health_tracker.dto.response.DoctorInfo;
import com.rahat.health_tracker.dto.response.DoctorProfileResponseDto;
import com.rahat.health_tracker.entity.doctor.DoctorProfile;
import com.rahat.health_tracker.service.DoctorProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorProfileService doctorProfileService;

//    @GetMapping("/get-profile")
//    public ResponseEntity<String> getDoctor() {
//        System.out.println("Hello profile");
//        Doctor doctor = SecurityContextResolver.getCurrentDoctor();
//        return ResponseEntity.ok("Hello: " + doctor.getName());
//    }

    @GetMapping("/get-all-info")
    public ResponseEntity<DoctorInfo> getInfo() {
        return ResponseEntity.ok(doctorProfileService.getInfo());
    }

    @PostMapping("/create-profile")
    public ResponseEntity<DoctorProfileResponseDto> createProfile(@Valid @RequestBody DoctorProfileRequestDto request) {
        DoctorProfileResponseDto responseDto = doctorProfileService.createProfile(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<DoctorProfile> updateProfile(@Valid @RequestBody DoctorProfileRequestDto doctorProfileRequestDto) {
        DoctorProfile doctorProfile = doctorProfileService.updateProfile(doctorProfileRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(doctorProfile);
    }

    @GetMapping("/get-profile")
    public ResponseEntity<DoctorProfileResponseDto> getDoctorProfile() {
        return ResponseEntity.ok(doctorProfileService.getDoctorProfile());
    }

    @PostMapping("/apply-chamber")
    public ResponseEntity<Boolean> applyChamber(@Valid @RequestBody ApplyChamberRequestDto request
    ) {
        return ResponseEntity.ok(doctorProfileService.applyChamber(request));
    }





//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }

//    @PostMapping("/register")
//    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegistrationRequest request) {
//        UserResponseDto response = authService.registerUser(request);
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<LogInResponseDto> login(@Valid  @RequestBody LogInRequestDto request) {
//        return ResponseEntity.ok(authService.loginUser(request));
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<LogInResponseDto> refreshToken(
//            @Valid @RequestBody RefreshTokenDto request) {
//        LogInResponseDto response = authService.refreshAccessToken(request.getRefreshToken());
//        return ResponseEntity.ok(response);
//    }
}
