package com.rootandfruit.server.dto;

import java.time.LocalDate;
import java.util.List;

public record RecipientDto(
        String recipientName,
        String recipientPhone,
        String recipientAddress,
        String recipientAddressDetail,
        String recipientPostCode,
        List<ProductDto> productInfo,
        LocalDate deliveryDate
) {
}
