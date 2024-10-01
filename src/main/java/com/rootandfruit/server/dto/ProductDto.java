package com.rootandfruit.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDto(
        @NotNull(message = "상품ID는 null값일 수 없습니다.")
        Long productId,
        @NotBlank(message = "상품명은 빈 값일 수 없습니다.")
        String productName,
        int productCount
) {
}