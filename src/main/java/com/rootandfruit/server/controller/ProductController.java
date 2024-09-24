package com.rootandfruit.server.controller;

import com.rootandfruit.server.dto.ProductRequestDto;
import com.rootandfruit.server.dto.ProductResponseDto;
import com.rootandfruit.server.dto.ProductSailedResponseDto;
import com.rootandfruit.server.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("product/all")
    public ResponseEntity<ProductResponseDto> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("product/sailed")
    public ResponseEntity<ProductSailedResponseDto> getSailedProducts() {
        return ResponseEntity.ok(productService.getSailedProducts());
    }

    @PatchMapping("product/{productId}")
    public ResponseEntity<Void> changeProduct(
            @PathVariable Long productId
    ) {
        productService.changeProductSailedStatus(productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("product")
    public ResponseEntity<Void> postProduct(
            @RequestBody ProductRequestDto productRequestDto
    ) {
        productService.createNewProduct(productRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("product/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId
    ) {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }
}
