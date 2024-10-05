package com.rootandfruit.server.api.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderDto(
        Long deliveryId,
        int orderNumber,
        String senderName,
        String senderPhone,
        String recipientName,
        String recipientPhone,
        String recipientAddress,
        String recipientAddressDetail,
        String recipientPostCode,
        List<String> productList,
        int productTotalCount,
        String deliveryStatus,
        LocalDate orderReceivedDate,
        LocalDate deliveryDate
) {
    public static OrderDto of(
            final Long deliveryId,
            final int orderNumber,
            final String senderName,
            final String senderPhone,
            final String recipientName,
            final String recipientPhone,
            final String recipientAddress,
            final String recipientAddressDetail,
            final String recipientPostCode,
            final List<String> productList,
            final int productTotalCount,
            final String deliveryStatus,
            final LocalDate orderReceivedDate,
            LocalDate deliveryDate
    ) {
        return new OrderDto(
                deliveryId,
                orderNumber,
                senderName,
                senderPhone,
                recipientName,
                recipientPhone,
                recipientAddress,
                recipientAddressDetail,
                recipientPostCode,
                productList,
                productTotalCount,
                deliveryStatus,
                orderReceivedDate,
                deliveryDate
        );
    }
}
