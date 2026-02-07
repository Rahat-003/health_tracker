package com.rahat.health_tracker.exception.auth;

// Thrown when login password is incorrect
public class IncorrectPasswordException extends UserAuthException {
    public IncorrectPasswordException(String email) {
        super("Incorrect password for email: " + email);
    }
}
