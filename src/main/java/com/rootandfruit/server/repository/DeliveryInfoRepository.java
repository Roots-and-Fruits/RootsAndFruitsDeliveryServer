package com.rootandfruit.server.repository;

import com.rootandfruit.server.domain.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {
}
