package com.rootandfruit.server.service;

import com.rootandfruit.server.domain.DeliveryInfo;
import com.rootandfruit.server.domain.DeliveryStatus;
import com.rootandfruit.server.domain.Product;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import com.rootandfruit.server.repository.DeliveryInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryInfoRepository deliveryInfoRepository;

    @Transactional
    public void statusCompletedToShipped(List<Long> deliveryInfoIds) {
        List<DeliveryInfo> deliveryInfos = deliveryInfoRepository.findAllById(deliveryInfoIds);
        if (deliveryInfos.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_DELIVERY_ERROR);
        }
        deliveryInfos.forEach(deliveryInfo -> deliveryInfo.changeDeliveryStatus(DeliveryStatus.ORDER_SHIPPED));
    }
}
