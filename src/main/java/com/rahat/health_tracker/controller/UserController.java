package com.rahat.health_tracker.controller;


import com.rahat.health_tracker.entity.User;
import com.rahat.health_tracker.enums.Role;
import com.rahat.health_tracker.repository.UserRepository;
import com.rahat.health_tracker.security.AuthPrincipal;
import com.rahat.health_tracker.security.SecurityUtils;
import com.rahat.health_tracker.utils.SecurityContextResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/get-profile")
    public ResponseEntity<String> getDoctor() {
        System.out.println("Hello profile");

        User user = SecurityContextResolver.getCurrentUser();
        return ResponseEntity.ok("Hello: " + user.getName());
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
