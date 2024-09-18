package com.rootandfruit.server.repository;

import com.rootandfruit.server.domain.OrderMetaData;
import com.rootandfruit.server.domain.Product;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMetaDataRepository extends JpaRepository<OrderMetaData, Long> {

    Optional<OrderMetaData> findOrderMetaDataById(Long id);

    default OrderMetaData findOrderMetaDataByIdOrThrow(Long id) {
        return findOrderMetaDataById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_ORDER_META_DATA_ERROR));
    }
}
