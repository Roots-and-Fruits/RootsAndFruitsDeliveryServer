package com.rootandfruit.server.api.dto;

import java.util.List;

public record OrderNumberResponseDto(
        String senderName,
        List<OrderNumberDto> orderList,
        int totalPrice
) {
    public static OrderNumberResponseDto of(
            final String senderName,
            final List<OrderNumberDto> orderList,
            final int totalPrice
    ) {
        return new OrderNumberResponseDto(
                senderName, orderList, totalPrice
        );
    }
}
