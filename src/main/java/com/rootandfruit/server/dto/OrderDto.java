package com.rootandfruit.server.dto;

import java.time.LocalDate;

public record OrderDto(
        String senderName,
        String recipientName,
        String productName,
        int productCount,
        String deliveryStatus,
        LocalDate orderReceivedDate,
        LocalDate deliveryDate
) {
    public static OrderDto of(
            final String senderName,
            final String recipientName,
            final String productName,
            final int productCount,
            final String deliveryStatus,
            final LocalDate orderReceivedDate,
            final LocalDate deliveryDate
    ) {
        return new OrderDto(
                senderName, recipientName, productName, productCount, deliveryStatus, orderReceivedDate, deliveryDate
        );
    }
}
