package com.rootandfruit.server.repository;

import com.rootandfruit.server.domain.Orders;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long>, OrdersCustomRepository {
    List<Orders> findByOrderNumber(int orderNumber);

    default List<Orders> findByOrderNumberOrThrow(int orderNumber) {
        List<Orders> orders = findByOrderNumber(orderNumber);
        if (orders.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_ORDER_ERROR);
        }
        return orders;
    }
}
