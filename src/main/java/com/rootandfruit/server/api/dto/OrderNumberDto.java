package com.rootandfruit.server.api.dto;

public record OrderNumberDto(
        String productName,
        int productCount,
        String deliveryStatus,
        int price,
        String orderTimeInfo

) {
    public static OrderNumberDto of(
            final String productName,
            final int productCount,
            final String deliveryStatus,
            final int price,
            final String orderTimeInfo
    ) {
        return new OrderNumberDto(
                productName, productCount, deliveryStatus, price, orderTimeInfo
        );
    }
}
