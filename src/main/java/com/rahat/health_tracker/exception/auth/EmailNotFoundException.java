package com.rahat.health_tracker.exception.auth;


public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super("Email doesn't exist: " + email);
    }
}

