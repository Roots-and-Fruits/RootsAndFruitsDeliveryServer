package com.rootandfruit.server.dto;

import java.util.List;

public record ProductSailedResponseDto(
    List<ProductTmpDto> sailedProductList
) {
    public static ProductSailedResponseDto of(
            final List<ProductTmpDto> sailedProductList
    ) {
        return new ProductSailedResponseDto(sailedProductList);
    }
}
