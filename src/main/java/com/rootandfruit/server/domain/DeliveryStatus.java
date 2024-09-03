package com.rootandfruit.server.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum DeliveryStatus {
    // 접수완료
    ORDER_ACCEPTED("ORDER_ACCEPTED"),
    // 결제완료
    PAYMENT_COMPLETED("PAYMENT_COMPLETED"),
    // 결제취소
    PAYMENT_CANCELED("PAYMENT_CANCELED"),
    // 발송완료
    ORDER_SHIPPED("ORDER_SHIPPED");

    private final String deliveryStatus;
}