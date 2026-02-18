//package com.rahat.health_tracker.security;
//
//import com.rahat.health_tracker.entity.User;
//import com.rahat.health_tracker.repository.UserRepository;
//import com.sun.security.auth.UserPrincipal;
//import lombok.RequiredArgsConstructor;
//import org.jspecify.annotations.NullMarked;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@NullMarked
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException(email));
//
//        return new AuthPrincipal(user.getEmail(), user.getRole());
//    }
//
//}
