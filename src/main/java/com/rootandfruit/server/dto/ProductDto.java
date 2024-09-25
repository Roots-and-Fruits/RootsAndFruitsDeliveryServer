package com.rootandfruit.server.dto;

public record ProductDto(
        Long productId,
        String productName,
        int productCount
) {
}