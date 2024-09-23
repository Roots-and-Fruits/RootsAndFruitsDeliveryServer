package com.rootandfruit.server.repository;

import com.rootandfruit.server.domain.DeliveryStatus;
import com.rootandfruit.server.domain.Orders;
import java.time.LocalDate;
import java.util.List;

public interface OrdersCustomRepository {
    List<Orders> searchOrders(LocalDate orderReceivedDate, LocalDate deliveryDate, String productName,
                              DeliveryStatus deliveryStatus);
}
