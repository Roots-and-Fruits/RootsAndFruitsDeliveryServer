package com.rootandfruit.server.controller;

import com.rootandfruit.server.controller.docs.DeliveryControllerDocs;
import com.rootandfruit.server.service.DeliveryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class DeliveryController implements DeliveryControllerDocs {

    private final DeliveryService deliveryService;

    @PatchMapping("/delivery/shipped")
    public ResponseEntity<Void> statusToShipped(
            @RequestBody List<Long> deliveryInfoIds
    ) {
        deliveryService.statusCompletedToShipped(deliveryInfoIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("delivery/max")
    public ResponseEntity<Integer> getAllowedDeliveryDay() {
        return ResponseEntity.ok(deliveryService.getAllowedDay());
    }

    @PatchMapping("delivery/{allowedDeliveryDays}")
    public ResponseEntity<Void> changeDay(
            @PathVariable int allowedDeliveryDays
    ) {

        deliveryService.changeAllowedDay(allowedDeliveryDays);
        return ResponseEntity.ok().build();
    }
}
