package com.rootandfruit.server.dto;

public record ProductAllDto(
        Long productId,
        String productName,
        int productPrice,
        boolean isSailed

) {
    public static ProductAllDto of(
            final Long productId,
            final String productName,
            final int productPrice,
            final boolean isSailed

    ) {

        return new ProductAllDto(
                productId, productName, productPrice, isSailed
        );
    }
}