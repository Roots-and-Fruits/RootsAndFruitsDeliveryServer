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
                                               DeliveryStatus deliveryStatus, Long cursorOrderId, String senderName,
                                               String recipientName, Integer orderNumber) {
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
                        cursorCondition(cursorOrderId),
                        eqSenderName(senderName),
                        eqRecipientName(recipientName),
                        eqOrderNumber(orderNumber)
                )
                .orderBy(orders.id.desc())
                .limit(limit);
        return query.fetch();
    }

    private BooleanExpression eqSenderName(String senderName) {
        return senderName != null ? QDeliveryInfo.deliveryInfo.senderName.eq(senderName) : null;
    }

    private BooleanExpression eqRecipientName(String recipientName) {
        return recipientName != null ? QDeliveryInfo.deliveryInfo.recipientName.eq(recipientName) : null;
    }

    private BooleanExpression eqOrderNumber(Integer orderNumber) {
        return orderNumber != null ? orders.orderNumber.eq(orderNumber) : null;
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
