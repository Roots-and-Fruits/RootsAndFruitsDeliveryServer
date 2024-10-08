package com.rootandfruit.server.api.repository;

import com.rootandfruit.server.api.domain.DeliveryStatus;
import com.rootandfruit.server.api.domain.Orders;
import com.rootandfruit.server.api.dto.RecentOrderResponseDto;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import java.util.List;
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

    @Query("SELECT new com.rootandfruit.server.api.dto.RecentOrderResponseDto(o.orderNumber, d.senderName) " +
            "FROM Orders o " +
            "JOIN o.deliveryInfo d " +
            "WHERE d.deliveryStatus = :deliveryStatus " +
            "ORDER BY o.createdAt DESC")
    List<RecentOrderResponseDto> findRecentOrdersByDeliveryStatus(
            @Param("deliveryStatus") DeliveryStatus deliveryStatus,
            Pageable pageable
    );
}
