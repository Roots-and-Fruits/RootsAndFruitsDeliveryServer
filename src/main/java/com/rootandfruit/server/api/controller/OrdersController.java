package com.rootandfruit.server.api.controller;

import com.rootandfruit.server.api.controller.docs.OrdersControllerDocs;
import com.rootandfruit.server.api.dto.OrderNumberResponseDto;
import com.rootandfruit.server.api.dto.OrderRequestDto;
import com.rootandfruit.server.api.dto.OrderResponseDto;
import com.rootandfruit.server.api.dto.RecentOrderResponseDto;
import com.rootandfruit.server.api.service.OrdersService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class OrdersController implements OrdersControllerDocs {

    private final OrdersService ordersService;

    @PostMapping("order")
    public ResponseEntity<Integer> order(
            @Valid @RequestBody OrderRequestDto orderRequestDto
    ) {
        return ResponseEntity.ok(ordersService.order(orderRequestDto));
    }

    @GetMapping("order/{orderNumber}")
    public ResponseEntity<OrderNumberResponseDto> order(
            @PathVariable int orderNumber
    ) {
        return ResponseEntity.ok(ordersService.getOrderByOrderNumber(orderNumber));
    }

    @GetMapping("order")
    public ResponseEntity<OrderResponseDto> searchFieldPosition(
            @RequestParam(required = false) final LocalDate orderReceivedDate,
            @RequestParam(required = false) final LocalDate deliveryDate,
            @RequestParam(required = false) final String productName,
            @RequestParam(required = false) final String deliveryStatus
    ) {

        return ResponseEntity.ok(ordersService.searchOrder(
                orderReceivedDate,
                deliveryDate,
                productName,
                deliveryStatus
        ));
    }

    @PatchMapping("order/pay/{orderNumber}")
    public ResponseEntity<Void> orderPay(
            @PathVariable int orderNumber
    ) {
        ordersService.pay(orderNumber);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("order/cancel/{orderNumber}")
    public ResponseEntity<Void> orderCancel(
            @PathVariable int orderNumber
    ) {
        ordersService.cancel(orderNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("order/recent")
    public ResponseEntity<List<RecentOrderResponseDto>> getRecentOrderNumber() {
        return ResponseEntity.ok(ordersService.getRecentOrders());
    }
}
