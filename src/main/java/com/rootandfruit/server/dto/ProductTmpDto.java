package com.rootandfruit.server.dto;

public record ProductTmpDto(
        Long productId,
        String productName,
        int productPrice

) {
    public static ProductTmpDto of(
            final Long productId,
            final String productName,
            final int productPrice

    ) {

        return new ProductTmpDto(
                productId, productName, productPrice
        );
    }
}
