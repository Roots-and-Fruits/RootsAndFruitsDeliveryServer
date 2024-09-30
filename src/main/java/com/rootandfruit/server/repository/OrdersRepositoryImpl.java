package com.rootandfruit.server.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rootandfruit.server.domain.DeliveryStatus;
import com.rootandfruit.server.domain.Orders;
import com.rootandfruit.server.domain.QDeliveryInfo;
import com.rootandfruit.server.domain.QOrders;
import com.rootandfruit.server.domain.QProduct;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class OrdersRepositoryImpl implements OrdersCustomRepository{

    private final JPAQueryFactory queryFactory;
    private final QOrders orders = QOrders.orders;

    public OrdersRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);;
    }

    @Override
    public List<Orders> searchOrders(LocalDate orderReceivedDate, LocalDate deliveryDate, String productName,
                                     DeliveryStatus deliveryStatus) {
        return queryFactory
                .selectFrom(orders)
                .join(orders.deliveryInfo, QDeliveryInfo.deliveryInfo)
                .join(orders.product, QProduct.product)
                .where(
                        orderReceivedDate != null ? orders.createdAt.year().eq(orderReceivedDate.getYear())
                                .and(orders.createdAt.month().eq(orderReceivedDate.getMonthValue()))
                                .and(orders.createdAt.dayOfMonth().eq(orderReceivedDate.getDayOfMonth())) : null,
                        ltDeliveryDate(deliveryDate),
                        eqProductName(productName),
                        eqDeliveryStatus(deliveryStatus)
                )
                .orderBy(QDeliveryInfo.deliveryInfo.deliveryDate.asc())
                .fetch();
    }

    private BooleanExpression ltDeliveryDate(LocalDate deliveryDate) {
        return deliveryDate != null ? QDeliveryInfo.deliveryInfo.deliveryDate.loe(deliveryDate) : null;
    }

    private BooleanExpression eqProductName(String productName) {
        return productName != null ? QProduct.product.productName.eq(productName) : null;
    }

    private BooleanExpression eqDeliveryStatus(DeliveryStatus deliveryStatus) {
        return deliveryStatus != null ? QDeliveryInfo.deliveryInfo.deliveryStatus.eq(
                deliveryStatus) : null;
    }
}
