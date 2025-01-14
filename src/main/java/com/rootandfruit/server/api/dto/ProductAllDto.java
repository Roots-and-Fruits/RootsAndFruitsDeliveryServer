package com.rootandfruit.server.api.dto;

public record ProductAllDto(
        Long productId,
        String productName,
        int productPrice,
        boolean isSailed,
        int sequence

) {
    public static ProductAllDto of(
            final Long productId,
            final String productName,
            final int productPrice,
            final boolean isSailed,
            final int sequence

    ) {

        return new ProductAllDto(
                productId, productName, productPrice, isSailed, sequence
        );
    }
}