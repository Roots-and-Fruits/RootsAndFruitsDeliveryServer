package com.rootandfruit.server.api.dto;

import java.util.List;

public record ProductSailedResponseDto(
    List<ProductTmpDto> trialSailedProductList,
    List<ProductTmpDto> sailedproductList
) {
    public static ProductSailedResponseDto of(
            final List<ProductTmpDto> trialSailedProductList,
            final List<ProductTmpDto> sailedproductList
    ) {
        return new ProductSailedResponseDto(trialSailedProductList, sailedproductList);
    }
}
