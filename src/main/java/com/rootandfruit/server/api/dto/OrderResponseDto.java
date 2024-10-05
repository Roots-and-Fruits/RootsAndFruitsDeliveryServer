package com.rootandfruit.server.api.dto;

import java.util.List;

public record OrderResponseDto(
        List<OrderDto> orderList
) {
    public static OrderResponseDto of(
            List<OrderDto> orderList
    ) {
        return new OrderResponseDto(orderList);
    }
}
