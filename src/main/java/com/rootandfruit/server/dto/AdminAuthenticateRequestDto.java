package com.rootandfruit.server.dto;

public record AdminAuthenticateRequestDto(
        String username,
        String password
) {
}
