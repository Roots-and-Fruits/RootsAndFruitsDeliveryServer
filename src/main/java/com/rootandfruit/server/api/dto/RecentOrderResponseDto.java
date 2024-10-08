package com.rootandfruit.server.api.dto;

public record RecentOrderResponseDto(
        Integer orderNumber,
        String senderName
) {
    public static RecentOrderResponseDto of(
            final Integer orderNumber,
            final String senderName
    ) {
        return new RecentOrderResponseDto(
                orderNumber, senderName
        );
    }
}
