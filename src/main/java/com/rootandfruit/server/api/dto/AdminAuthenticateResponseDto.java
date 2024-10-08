package com.rootandfruit.server.api.dto;

public record AdminAuthenticateResponseDto(
    String token
) {
    public static AdminAuthenticateResponseDto of(
            final String token
    ) {
        return new AdminAuthenticateResponseDto(token);
    }
}
