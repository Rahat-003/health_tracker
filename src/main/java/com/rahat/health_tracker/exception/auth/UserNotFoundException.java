package com.rahat.health_tracker.exception.auth;

public class UserNotFoundException extends UserAuthException {
    public UserNotFoundException(String email) {
        super("User not found with email: " + email);
    }
}
