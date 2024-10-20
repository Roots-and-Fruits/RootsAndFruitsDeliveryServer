package com.rootandfruit.server.api.repository;

import com.rootandfruit.server.api.domain.DeliveryStatus;
import com.rootandfruit.server.api.domain.Orders;
import java.time.LocalDate;
import java.util.List;

public interface OrdersCustomRepository {
    List<Orders> searchOrders(LocalDate orderReceivedDate, LocalDate deliveryDate, String productName,
                              DeliveryStatus deliveryStatus);
}
