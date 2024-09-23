package com.rootandfruit.server.controller;

import com.rootandfruit.server.dto.OrderNumberResponseDto;
import com.rootandfruit.server.dto.OrderRequestDto;
import com.rootandfruit.server.dto.OrderResponseDto;
import com.rootandfruit.server.service.OrdersService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping("order")
    public ResponseEntity<Void> order(
            @RequestBody OrderRequestDto orderRequestDto
            ){
        ordersService.order(orderRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("order/{orderNumber}")
    public ResponseEntity<OrderNumberResponseDto> order(
            @PathVariable int orderNumber
    ){
        return ResponseEntity.ok(ordersService.getOrderByOrderNumber(orderNumber));
    }

    @GetMapping("/order")
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
}
