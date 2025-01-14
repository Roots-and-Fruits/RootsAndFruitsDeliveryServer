package com.rootandfruit.server.api.repository;

import com.rootandfruit.server.api.domain.DeliveryStatus;
import com.rootandfruit.server.api.domain.Orders;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalDate;
import java.util.List;

public interface OrdersCustomRepository {
    List<Orders> searchOrders(LocalDate orderReceivedDate, LocalDate deliveryDate, String productName,
                              DeliveryStatus deliveryStatus);

    List<Orders> searchOrdersWithCursor(LocalDate orderReceivedDate, LocalDate deliveryDate, String productName,
                                        DeliveryStatus deliveryStatus, Long cursorOrderId, String senderName,
                                        String recipientName, Integer orderNumber);
}
