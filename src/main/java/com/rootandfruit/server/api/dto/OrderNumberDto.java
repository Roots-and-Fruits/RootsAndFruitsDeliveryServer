package com.rootandfruit.server.api.dto;

public record OrderNumberDto(
        String productName,
         int productCount,
         String deliveryStatus,
         int price
) {
    public static OrderNumberDto of(
            final String productName,
            final int productCount,
            final String deliveryStatus,
            final int price
    ) {
        return new OrderNumberDto(
                productName, productCount, deliveryStatus, price
        );
    }
}
