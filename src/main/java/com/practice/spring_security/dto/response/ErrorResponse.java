package com.practice.spring_security.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
    int status,
    String message,
    Map<String, String> fieldErrors,
    LocalDateTime timestamp
) {

    public ErrorResponse(int status, String message, Map<String, String> fieldErrors) {
        this(status, message, fieldErrors, LocalDateTime.now());
    }

    public ErrorResponse(int status, String message) {
        this(status, message, null, LocalDateTime.now());
    }
}