package com.rootandfruit.server.api.repository;

import com.rootandfruit.server.api.domain.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {

}
