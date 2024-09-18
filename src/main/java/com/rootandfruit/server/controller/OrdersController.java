package com.rootandfruit.server.controller;

import com.rootandfruit.server.dto.OrderRequestDto;
import com.rootandfruit.server.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping("orders")
    public ResponseEntity<Void> order(
            @RequestBody OrderRequestDto orderRequestDto
            ){
        ordersService.order(orderRequestDto);
        return ResponseEntity.ok().build();
    }
}
