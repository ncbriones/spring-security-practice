package com.practice.spring_security.dto.response;

public record RegisterResponse(
    String token,
    String type
) {
}
