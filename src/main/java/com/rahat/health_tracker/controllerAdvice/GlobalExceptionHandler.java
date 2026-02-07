package com.rahat.health_tracker.controllerAdvice;

import com.rahat.health_tracker.exception.auth.EmailAlreadyExistsException;
import com.rahat.health_tracker.exception.auth.IncorrectPasswordException;
import com.rahat.health_tracker.exception.auth.UserAuthException;
import com.rahat.health_tracker.exception.auth.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailExists(EmailAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error(ex.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPassword(IncorrectPasswordException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error(ex.getMessage(), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(UserAuthException.class)
    public ResponseEntity<Map<String, Object>> handleUserAuthException(UserAuthException ex) {
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                "status", HttpStatus.UNAUTHORIZED.value(),
                "error", "Unauthorized",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOtherExceptions(Exception ex) {
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error", "Internal Server Error",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private Map<String, Object> error(String message, HttpStatus status) {
        return Map.of(
                "timestamp", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        );
    }
}
