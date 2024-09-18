package com.rootandfruit.server.domain;

import com.rootandfruit.server.dto.OrderRequestDto;
import com.rootandfruit.server.dto.RecipientDto;
import com.rootandfruit.server.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "deliveryinfo")
public class DeliveryInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "sender_phone", nullable = false)
    private String senderPhone;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_phone", nullable = false)
    private String recipientPhone;

    @Column(name = "recipient_address", nullable = false)
    private String recipientAddress;

    @Column(name = "recipient_address_detail", nullable = false)
    private String recipientAddressDetail;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false)
    private DeliveryStatus deliveryStatus;

    @Builder
    private DeliveryInfo(
            final String senderName,
            final String senderPhone,
            final String recipientName,
            final String recipientPhone,
            final String recipientAddress,
            final String recipientAddressDetail,
            final LocalDate deliveryDate,
            final  DeliveryStatus deliveryStatus
    ){
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.recipientAddress = recipientAddress;
        this.recipientAddressDetail = recipientAddressDetail;
        this.deliveryDate = deliveryDate;
        this.deliveryStatus = deliveryStatus;
    }

    public static DeliveryInfo createDeliveryInfo(final OrderRequestDto orderRequestDto, final RecipientDto recipientDto) {
        return DeliveryInfo.builder()
                .senderName(orderRequestDto.senderName())
                .senderPhone(orderRequestDto.senderPhone())
                .recipientName(recipientDto.recipientName())
                .recipientPhone(recipientDto.recipientPhone())
                .recipientAddress(recipientDto.recipientAddress())
                .recipientAddressDetail(recipientDto.recipientAddressDetail())
                .deliveryDate(recipientDto.deliveryDate())
                .deliveryStatus(DeliveryStatus.ORDER_ACCEPTED)
                .build();
    }
}
