package com.rootandfruit.server.api.domain;

import com.rootandfruit.server.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_count", nullable = false)
    private int productCount;

    @Column(name = "order_number", nullable = false)
    private int orderNumber;

    // 주문과 사용자 간의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 주문과 상품 간의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 주문과 배송정보 간의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_info_id", nullable = false)
    private DeliveryInfo deliveryInfo;

    @Builder
    private Orders(
            final int productCount,
            final int orderNumber,
            final Member member,
            final Product product,
            final DeliveryInfo deliveryInfo
    ) {
        this.productCount = productCount;
        this.orderNumber = orderNumber;
        this.member = member;
        this.product = product;
        this.deliveryInfo = deliveryInfo;
    }

    public static Orders createOrders(
            final int productCount,
            final int orderNumber,
            final Member member,
            final Product product,
            final DeliveryInfo deliveryInfo
    ) {
        return Orders.builder()
                .productCount(productCount)
                .orderNumber(orderNumber)
                .member(member)
                .product(product)
                .deliveryInfo(deliveryInfo)
                .build();
    }
}
