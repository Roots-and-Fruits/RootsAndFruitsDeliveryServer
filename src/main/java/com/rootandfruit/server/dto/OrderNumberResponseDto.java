package com.rootandfruit.server.dto;

import java.util.List;

public record OrderNumberResponseDto(
        String senderName,
        List<OrderDto> orderList,
        int totalPrice
) {
    public static OrderNumberResponseDto of(
            final String senderName,
            final List<OrderDto> orderList,
            final int totalPrice
    ) {
        return new OrderNumberResponseDto(
                senderName, orderList, totalPrice
        );
    }
}
