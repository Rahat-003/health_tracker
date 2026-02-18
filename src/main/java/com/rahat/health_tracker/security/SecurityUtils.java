package com.rahat.health_tracker.security;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;


public final class SecurityUtils {

    private SecurityUtils() {}

    public static AuthPrincipal current() {
        return (AuthPrincipal)
                Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication())
                        .getPrincipal();
    }
}
