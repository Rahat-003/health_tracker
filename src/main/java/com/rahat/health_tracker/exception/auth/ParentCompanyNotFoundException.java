package com.rahat.health_tracker.exception.auth;


public class ParentCompanyNotFoundException extends RuntimeException {
    public ParentCompanyNotFoundException(Long id) {
        super("Parent Company doesn't exist for id: " + id);
    }
}

