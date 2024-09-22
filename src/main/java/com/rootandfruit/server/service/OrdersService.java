package com.rootandfruit.server.service;

import com.rootandfruit.server.domain.DeliveryInfo;
import com.rootandfruit.server.domain.Member;
import com.rootandfruit.server.domain.OrderMetaData;
import com.rootandfruit.server.domain.Orders;
import com.rootandfruit.server.domain.Product;
import com.rootandfruit.server.dto.OrderRequestDto;
import com.rootandfruit.server.repository.DeliveryInfoRepository;
import com.rootandfruit.server.repository.MemberRepository;
import com.rootandfruit.server.repository.OrderMetaDataRepository;
import com.rootandfruit.server.repository.OrdersRepository;
import com.rootandfruit.server.repository.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final DeliveryInfoRepository deliveryInfoRepository;
    private final OrdersRepository ordersRepository;
    private final OrderMetaDataRepository orderMetaDataRepository;

    @Transactional
    public void order(OrderRequestDto orderRequestDto){
        Member member = Member.createMember(orderRequestDto.senderName(), orderRequestDto.senderPhone(),
                orderRequestDto.isMarketingConsent());
        memberRepository.save(member);

        orderRequestDto.recipientInfo().forEach(recipientDto -> {

            DeliveryInfo deliveryInfo = DeliveryInfo.createDeliveryInfo(orderRequestDto, recipientDto);
            deliveryInfoRepository.save(deliveryInfo);

            OrderMetaData orderMetaData = orderMetaDataRepository.findOrderMetaDataByIdOrThrow(1L);
            int currentOrderNumber = orderMetaData.incrementOrderNumberSequence();

            recipientDto.productInfo().forEach(productDto -> {
                if (productDto.productCount() > 0) {
                    Product product = productRepository.findProductByIdOrThrow(productDto.productId());
                    Orders order = Orders.createOrders(
                            productDto.productCount(),
                            currentOrderNumber,
                            member,
                            product,
                            deliveryInfo
                    );
                    ordersRepository.save(order);
                }
            });
        });
    }
}
