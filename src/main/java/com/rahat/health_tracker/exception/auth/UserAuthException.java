package com.rahat.health_tracker.exception.auth;


public abstract class UserAuthException extends RuntimeException {

    protected UserAuthException(String message) {
        super(message);
    }
}

