package com.rootandfruit.server.api.dto;

import java.util.List;

public record OrderCursorResponseDto(
        List<OrderDto> orders,
        Long nextCursor
) {
    public static OrderCursorResponseDto of(List<OrderDto> orders, Long nextCursor) {
        return new OrderCursorResponseDto(orders, nextCursor);
    }
}
