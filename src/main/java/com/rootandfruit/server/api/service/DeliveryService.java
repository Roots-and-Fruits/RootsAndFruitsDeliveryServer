package com.rootandfruit.server.api.service;

import com.rootandfruit.server.api.domain.DeliveryInfo;
import com.rootandfruit.server.api.domain.DeliveryStatus;
import com.rootandfruit.server.api.domain.OrderMetaData;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import com.rootandfruit.server.api.repository.DeliveryInfoRepository;
import com.rootandfruit.server.api.repository.OrderMetaDataRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryInfoRepository deliveryInfoRepository;
    private final OrderMetaDataRepository orderMetaDataRepository;

    @Transactional
    public void statusCompletedToShipped(List<Long> deliveryInfoIds) {
        List<DeliveryInfo> deliveryInfos = deliveryInfoRepository.findAllById(deliveryInfoIds);
        if (deliveryInfos.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_DELIVERY_ERROR);
        }
        deliveryInfos.forEach(deliveryInfo -> deliveryInfo.changeDeliveryStatus(DeliveryStatus.ORDER_SHIPPED));
    }

    @Transactional(readOnly = true)
    public int getAllowedDay() {
        OrderMetaData orderMetaData = orderMetaDataRepository.findOrderMetaDataByIdOrThrow(1L);
        return orderMetaData.getAllowedDeliveryDays();
    }

    @Transactional
    public void changeAllowedDay(int allowedDeliveryDays) {
        OrderMetaData orderMetaData = orderMetaDataRepository.findOrderMetaDataByIdOrThrow(1L);
        orderMetaData.changeAllowedDeliveryDays(allowedDeliveryDays);
    }
}
