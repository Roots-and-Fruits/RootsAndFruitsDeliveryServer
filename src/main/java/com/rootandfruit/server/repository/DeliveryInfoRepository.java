package com.rootandfruit.server.repository;

import com.rootandfruit.server.domain.DeliveryInfo;
import com.rootandfruit.server.domain.Product;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {

}
