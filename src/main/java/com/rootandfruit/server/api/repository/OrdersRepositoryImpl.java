package com.rootandfruit.server.api.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rootandfruit.server.api.domain.DeliveryStatus;
import com.rootandfruit.server.api.domain.Orders;
import com.rootandfruit.server.api.domain.QDeliveryInfo;
import com.rootandfruit.server.api.domain.QOrders;
import com.rootandfruit.server.api.domain.QProduct;
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

    @Override
    public List<Orders> searchOrdersWithCursor(LocalDate orderReceivedDate, LocalDate deliveryDate, String productName,
                                               DeliveryStatus deliveryStatus, Long cursorOrderId) {
        int limit = 50;
        JPAQuery<Orders> query = queryFactory
                .selectFrom(orders)
                .join(orders.deliveryInfo, QDeliveryInfo.deliveryInfo)
                .join(orders.product, QProduct.product)
                .where(
                        orderReceivedDate != null ? orders.createdAt.year().eq(orderReceivedDate.getYear())
                                .and(orders.createdAt.month().eq(orderReceivedDate.getMonthValue()))
                                .and(orders.createdAt.dayOfMonth().eq(orderReceivedDate.getDayOfMonth())) : null,
                        ltDeliveryDate(deliveryDate),
                        eqProductName(productName),
                        eqDeliveryStatus(deliveryStatus),
                        cursorCondition(cursorOrderId) // 커서 조건
                )
                .orderBy(orders.id.desc())
                .limit(limit);
        System.out.println("========================");
        System.out.println(query.toString());
        return query.fetch();
    }

    private BooleanExpression cursorCondition(Long cursorOrderId) {
        return cursorOrderId != null ? orders.id.lt(cursorOrderId) : null;
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

    private BooleanExpression eqIsTrial(boolean isTrial) {
        return QProduct.product.isTrial.eq(isTrial);
    }
}
