package com.rootandfruit.server.repository;

import com.rootandfruit.server.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
