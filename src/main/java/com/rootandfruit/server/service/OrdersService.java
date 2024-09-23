package com.rootandfruit.server.service;

import com.rootandfruit.server.domain.DeliveryInfo;
import com.rootandfruit.server.domain.DeliveryStatus;
import com.rootandfruit.server.domain.Member;
import com.rootandfruit.server.domain.OrderMetaData;
import com.rootandfruit.server.domain.Orders;
import com.rootandfruit.server.domain.Product;
import com.rootandfruit.server.dto.OrderDto;
import com.rootandfruit.server.dto.OrderNumberDto;
import com.rootandfruit.server.dto.OrderNumberResponseDto;
import com.rootandfruit.server.dto.OrderRequestDto;
import com.rootandfruit.server.dto.OrderResponseDto;
import com.rootandfruit.server.repository.DeliveryInfoRepository;
import com.rootandfruit.server.repository.MemberRepository;
import com.rootandfruit.server.repository.OrderMetaDataRepository;
import com.rootandfruit.server.repository.OrdersRepository;
import com.rootandfruit.server.repository.ProductRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

    @Transactional(readOnly = true)
    public OrderNumberResponseDto getOrderByOrderNumber(int orderNumber) {
        List<Orders> orders = ordersRepository.findByOrderNumberOrThrow(orderNumber);

        // 주문번호에 해당하는 모든 주문 항목을 매핑
        List<OrderNumberDto> orderNumberDtos = orders.stream()
                .map(order -> OrderNumberDto.of(
                        order.getProduct().getProductName(),
                        order.getProductCount(),
                        order.getDeliveryInfo().getDeliveryStatus().getDeliveryStatus(),
                        (order.getProduct().getPrice() * order.getProductCount())
                ))
                .collect(Collectors.toList());

        int totalPrice = orderNumberDtos.stream()
                .mapToInt(OrderNumberDto::price)
                .sum();

        return OrderNumberResponseDto.of(
                orders.get(0).getDeliveryInfo().getSenderName(),
                orderNumberDtos,
                totalPrice
        );
    }

    @Transactional(readOnly = true)
    public OrderResponseDto searchOrder(LocalDate orderReceivedDate, LocalDate deliveryDate, String productName,
                                        String deliveryStatus) {

        DeliveryStatus status = null;
        if (deliveryStatus != null) {
            status = DeliveryStatus.fromString(deliveryStatus);
        }

        List<Orders> orderlist = ordersRepository.searchOrders(orderReceivedDate, deliveryDate, productName, status);

        return OrderResponseDto.of(orderlist.stream()
                .map(orders -> OrderDto.of(
                        orders.getDeliveryInfo().getSenderName(),
                        orders.getDeliveryInfo().getRecipientName(),
                        orders.getProduct().getProductName(),
                        orders.getProductCount(),
                        orders.getDeliveryInfo().getDeliveryStatus().getDeliveryStatus(),
                        orders.getCreatedAt().toLocalDate(),
                        orders.getDeliveryInfo().getDeliveryDate()
                )).collect(Collectors.toList()));
    }
}
