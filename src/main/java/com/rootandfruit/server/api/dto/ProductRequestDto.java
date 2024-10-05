package com.rootandfruit.server.api.dto;

public record ProductRequestDto(
        String productName,
        int productPrice,
        boolean isTrial
) {
}
