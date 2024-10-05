package com.rootandfruit.server.api.dto;

public record AdminAuthenticateRequestDto(
        String username,
        String password
) {
}
