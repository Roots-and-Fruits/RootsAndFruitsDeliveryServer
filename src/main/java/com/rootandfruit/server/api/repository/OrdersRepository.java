package com.rootandfruit.server.api.repository;

import com.rootandfruit.server.api.domain.Admin;
import com.rootandfruit.server.api.domain.DeliveryStatus;
import com.rootandfruit.server.api.domain.Orders;
import com.rootandfruit.server.api.dto.RecentOrderResponseDto;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.hibernate.query.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository<Orders, Long>, OrdersCustomRepository {
    List<Orders> findByOrderNumber(int orderNumber);

    default List<Orders> findByOrderNumberOrThrow(int orderNumber) {
        List<Orders> orders = findByOrderNumber(orderNumber);
        if (orders.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_ORDER_ERROR);
        }
        return orders;
    }

    @Query("SELECT DISTINCT new com.rootandfruit.server.api.dto.RecentOrderResponseDto(o.orderNumber, d.senderName, d.deliveryStatus) " +
            "FROM Orders o " +
            "JOIN o.deliveryInfo d " +
            "ORDER BY o.orderNumber DESC")
    List<RecentOrderResponseDto> findRecentOrders();

    Optional<Orders> findOrdersById(Long id);

    default Orders findOrdersByIdOrThrow(Long id) {
        return findOrdersById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_ORDERS_ERROR));
    }
}
