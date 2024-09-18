package com.rootandfruit.server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ordermetadate")
public class OrderMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 주문번호 시퀀스 값
    @Column(name = "order_number_sequence")
    private int orderNumberSequence = 1000;

    // 배송 가능 날짜 범위
    @Column(name = "allowed_delivery_days")
    private int allowedDeliveryDays;

    public int incrementOrderNumberSequence() {
        return ++this.orderNumberSequence;
    }
}
