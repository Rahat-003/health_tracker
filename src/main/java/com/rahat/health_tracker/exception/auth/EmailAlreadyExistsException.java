package com.rahat.health_tracker.exception.auth;

// Thrown when registering a user with an existing email
public class EmailAlreadyExistsException extends UserAuthException {
    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}
