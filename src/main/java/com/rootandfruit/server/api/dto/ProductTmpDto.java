package com.rootandfruit.server.api.dto;

public record ProductTmpDto(
        Long productId,
        String productName,
        int productPrice,
        int sequence

) {
    public static ProductTmpDto of(
            final Long productId,
            final String productName,
            final int productPrice,
            final int sequence

    ) {

        return new ProductTmpDto(
                productId, productName, productPrice, sequence
        );
    }
}
