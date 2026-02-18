package com.rahat.health_tracker.security;


import com.rahat.health_tracker.entity.User;
import com.rahat.health_tracker.enums.Role;
import com.rahat.health_tracker.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@NullMarked
//@RequiredArgsConstructor
public class AuthPrincipal implements UserDetails {

    private final String email;

//    private final UserRepository userRepository;

    @Getter
    private final Role role;

    public AuthPrincipal(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override public String getUsername() { return email; }
    @Override public @Nullable String getPassword() { return null; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }


}

