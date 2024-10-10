package com.rootandfruit.server.api.service;

import com.rootandfruit.server.api.domain.DeliveryInfo;
import com.rootandfruit.server.api.domain.DeliveryStatus;
import com.rootandfruit.server.api.domain.Member;
import com.rootandfruit.server.api.domain.OrderMetaData;
import com.rootandfruit.server.api.domain.Orders;
import com.rootandfruit.server.api.domain.Product;
import com.rootandfruit.server.api.dto.OrderDto;
import com.rootandfruit.server.api.dto.OrderNumberDto;
import com.rootandfruit.server.api.dto.OrderNumberResponseDto;
import com.rootandfruit.server.api.dto.OrderRequestDto;
import com.rootandfruit.server.api.dto.OrderResponseDto;
import com.rootandfruit.server.api.dto.RecentOrderResponseDto;
import com.rootandfruit.server.api.repository.DeliveryInfoRepository;
import com.rootandfruit.server.api.repository.MemberRepository;
import com.rootandfruit.server.api.repository.OrderMetaDataRepository;
import com.rootandfruit.server.api.repository.OrdersRepository;
import com.rootandfruit.server.api.repository.ProductRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public int order(OrderRequestDto orderRequestDto) {
        Member member = Member.createMember(orderRequestDto.senderName(), orderRequestDto.senderPhone(),
                orderRequestDto.isMarketingConsent());
        memberRepository.save(member);
        OrderMetaData orderMetaData = orderMetaDataRepository.findOrderMetaDataByIdOrThrow(1L);
        int currentOrderNumber = orderMetaData.incrementOrderNumberSequence();

        orderRequestDto.recipientInfo().forEach(recipientDto -> {

            DeliveryInfo deliveryInfo = DeliveryInfo.createDeliveryInfo(orderRequestDto, recipientDto);
            deliveryInfoRepository.save(deliveryInfo);

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
        return currentOrderNumber;
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

        // 주문 목록 조회 (여기서 각 주문은 하나의 배송 정보에 속함)
        List<Orders> orderList = ordersRepository.searchOrders(orderReceivedDate, deliveryDate, productName, status);

        // 배송 정보별로 주문을 묶고 OrderDto로 변환
        Map<Long, List<Orders>> ordersByDeliveryInfo = orderList.stream()
                .collect(Collectors.groupingBy(order -> order.getDeliveryInfo().getId()));

        List<OrderDto> orderDtoList = ordersByDeliveryInfo.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    // 각 entry의 value(Orders 리스트)에서 첫 번째 주문의 deliveryDate로 비교
                    LocalDate deliveryDate1 = entry1.getValue().get(0).getDeliveryInfo().getDeliveryDate();
                    LocalDate deliveryDate2 = entry2.getValue().get(0).getDeliveryInfo().getDeliveryDate();
                    return deliveryDate1.compareTo(deliveryDate2); // 오름차순 정렬
                })
                .map(entry -> toOrderDto(entry.getKey(), entry.getValue())) // key는 배송정보 ID, value는 해당 배송에 속한 주문 리스트
                .collect(Collectors.toList());

        // OrderResponseDto로 반환
        return OrderResponseDto.of(orderDtoList);
    }


    private OrderDto toOrderDto(Long deliveryId, List<Orders> orders) {
        // 상품 목록 생성 (여러 주문의 상품 정보를 리스트로 변환)
        List<String> productList = orders.stream()
                .map(order -> order.getProduct().getProductName() + " " + order.getProductCount() + "EA")
                .collect(Collectors.toList());

        // 상품 수량 총합 (각 주문의 상품 수량을 합산)
        int productTotalCount = orders.stream()
                .mapToInt(Orders::getProductCount)
                .sum();

        Orders firstOrder = orders.get(0); // 같은 배송 정보에 속한 첫 번째 주문을 기준으로 배송 정보 가져옴

        return OrderDto.of(
                deliveryId,
                firstOrder.getOrderNumber(),
                firstOrder.getDeliveryInfo().getSenderName(),
                firstOrder.getDeliveryInfo().getSenderPhone(),
                firstOrder.getDeliveryInfo().getRecipientName(),
                firstOrder.getDeliveryInfo().getRecipientPhone(),
                firstOrder.getDeliveryInfo().getRecipientAddress(),
                firstOrder.getDeliveryInfo().getRecipientAddressDetail(),
                firstOrder.getDeliveryInfo().getRecipientPostCode(),
                productList,
                productTotalCount,
                firstOrder.getDeliveryInfo().getDeliveryStatus().getDeliveryStatus(),
                firstOrder.getCreatedAt().toLocalDate(),
                firstOrder.getDeliveryInfo().getDeliveryDate()
        );
    }

    @Transactional
    public void pay(int orderNumber) {
        List<Orders> orders = ordersRepository.findByOrderNumberOrThrow(orderNumber);

        for (Orders order : orders) {
            DeliveryInfo deliveryInfo = order.getDeliveryInfo();
            deliveryInfo.changeDeliveryStatus(DeliveryStatus.PAYMENT_COMPLETED);
        }
    }

    @Transactional
    public void cancel(int orderNumber) {
        List<Orders> orders = ordersRepository.findByOrderNumberOrThrow(orderNumber);

        for (Orders order : orders) {
            DeliveryInfo deliveryInfo = order.getDeliveryInfo();
            deliveryInfo.changeDeliveryStatus(DeliveryStatus.PAYMENT_CANCELED);
        }
    }

    @Transactional(readOnly = true)
    public List<RecentOrderResponseDto> getRecentOrders() {
        List<RecentOrderResponseDto> recentOrders = ordersRepository.findRecentOrders();

        return recentOrders.stream()
                .limit(10)
                .map(order -> RecentOrderResponseDto.of(
                        order.orderNumber(),
                        order.senderName(),
                        DeliveryStatus.fromString(order.deliveryStatus())// 여기서는 DeliveryStatus 타입을 그대로 전달
                ))
                .collect(Collectors.toList());
    }
}

