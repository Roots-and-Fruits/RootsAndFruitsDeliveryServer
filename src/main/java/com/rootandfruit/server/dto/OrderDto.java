package com.rootandfruit.server.dto;

public record OrderDto(
        String productName,
         int productCount,
         String orderState,
         int price
) {
    public static OrderDto of(
            final String productName,
            final int productCount,
            final String orderState,
            final int price
    ) {
        return new OrderDto(
                productName, productCount, orderState, price
        );
    }
}
