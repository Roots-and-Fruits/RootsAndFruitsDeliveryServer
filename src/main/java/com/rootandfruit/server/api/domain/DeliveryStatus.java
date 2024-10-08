package com.rootandfruit.server.api.domain;

import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
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

    public static DeliveryStatus fromString(String status) {
        for (DeliveryStatus deliveryStatus : DeliveryStatus.values()) {
            if (deliveryStatus.getDeliveryStatus().equals(status)) {
                return deliveryStatus;
            }
        }
        throw new CustomException(ErrorType.INVALID_DELIVERY_STATUS_ERROR);
    }
}