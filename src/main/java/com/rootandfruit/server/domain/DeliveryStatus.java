package com.rootandfruit.server.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum DeliveryStatus {

    // 접수완료
    ORDER_ACCEPTED("접수완료"),
    // 결제완료
    PAYMENT_COMPLETED("결제완료"),
    // 결제취소
    PAYMENT_CANCELED("결제취소"),
    // 발송완료
    ORDER_SHIPPED("발송완료");

    private final String deliveryStatus;
}