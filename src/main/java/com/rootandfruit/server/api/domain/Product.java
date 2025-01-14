package com.rootandfruit.server.api.domain;

import com.rootandfruit.server.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName ;

    @Column(name = "price", nullable = false)
    private int price ;

    @Column(name = "is_sailed")
    private boolean isSailed ;

    @Column(name = "is_deleted")
    private boolean isDeleted ;

    @Column(name = "is_trial")
    private boolean isTrial;

    @Column(name = "sequence", nullable = false)
    private int sequence; // 새로운 컬럼 추가


    @Builder
    private Product(
            final String productName,
            final int price,
            final boolean isTrial,
            final boolean isSailed,
            final boolean isDeleted,
            final int sequence
    ) {
        this.productName = productName;
        this.price = price;
        this.isTrial = isTrial;
        this.isSailed = isSailed;
        this.isDeleted = isDeleted;
        this.sequence = sequence;
    }

    public static Product createProduct(
            final String productName,
            final int price,
            final boolean isTrial,
            final boolean isSailed,
            final boolean isDeleted,
            final int sequence
    ) {
        return Product.builder()
                .productName(productName)
                .price(price)
                .isTrial(isTrial)
                .isSailed(isSailed)
                .isDeleted(isDeleted)
                .sequence(sequence)
                .build();
    }

    public void switchSailedStatus(boolean isSailed) {
        this.isSailed = !isSailed;
    }

    public void deleteProduct() {
        this.isDeleted = true;
    }

    public void updateSequence(int newSequence) {
        this.sequence = newSequence;
    }
}
