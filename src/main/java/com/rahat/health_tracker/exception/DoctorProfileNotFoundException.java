package com.rahat.health_tracker.exception;


public class DoctorProfileNotFoundException extends RuntimeException {
    public DoctorProfileNotFoundException() {
        super("Doctor profile doesn't exist");
    }
}

