package com.rootandfruit.server.api.dto;

import com.rootandfruit.server.api.domain.DeliveryStatus;

public record RecentOrderResponseDto(
        Integer orderNumber,
        String senderName,
        String deliveryStatus
) {
    // 생성자 추가
    public RecentOrderResponseDto(Integer orderNumber, String senderName, DeliveryStatus deliveryStatus) {
        this(orderNumber, senderName, convertStatusToKorean(deliveryStatus));
    }

    public static RecentOrderResponseDto of(
            final Integer orderNumber,
            final String senderName,
            final DeliveryStatus deliveryStatus
    ) {
        return new RecentOrderResponseDto(
                orderNumber, senderName, convertStatusToKorean(deliveryStatus)
        );
    }

    private static String convertStatusToKorean(DeliveryStatus deliveryStatus) {
        return switch (deliveryStatus) {
            case ORDER_ACCEPTED -> "접수완료";
            case PAYMENT_COMPLETED -> "결제완료";
            case PAYMENT_CANCELED -> "결제취소";
            case ORDER_SHIPPED -> "발송완료";
            default -> "알 수 없음";
        };
    }
}