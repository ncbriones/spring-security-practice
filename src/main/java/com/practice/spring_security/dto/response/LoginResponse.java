package com.practice.spring_security.dto.response;

import com.practice.spring_security.entity.Role;

public record LoginResponse(
    String token,
    String type,
    String username,
    Role role
) {}