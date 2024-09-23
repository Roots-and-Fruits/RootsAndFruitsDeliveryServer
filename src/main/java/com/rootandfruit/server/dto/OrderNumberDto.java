package com.rootandfruit.server.dto;

public record OrderDto(
        String productName,
         int productCount,
         String deliveryStatus,
         int price
) {
    public static OrderDto of(
            final String productName,
            final int productCount,
            final String deliveryStatus,
            final int price
    ) {
        return new OrderDto(
                productName, productCount, deliveryStatus, price
        );
    }
}
