package com.rootandfruit.server.dto;

public record ProductRequestDto(
        String productName,
        int productPrice,
        boolean isTrial
) {
}
