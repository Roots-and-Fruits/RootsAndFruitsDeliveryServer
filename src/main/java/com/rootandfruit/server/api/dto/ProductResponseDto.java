package com.rootandfruit.server.api.dto;

import java.util.List;

public record ProductResponseDto(
        List<ProductAllDto> trialProductList,
        List<ProductAllDto> productList
) {
    public static ProductResponseDto of(
            final List<ProductAllDto> trialProductList,
            final List<ProductAllDto> productList
    ) {
        return new ProductResponseDto(
                trialProductList, productList
        );
    }
}
