package com.rootandfruit.server.dto;

import java.util.List;

public record ProductResponseDto(
        List<ProductTmpDto> trialProductList,
        List<ProductTmpDto> productList
) {
    public static ProductResponseDto of(
            final List<ProductTmpDto> trialProductList,
            final List<ProductTmpDto> productList
    ) {
        return new ProductResponseDto(
                trialProductList, productList
        );
    }
}
