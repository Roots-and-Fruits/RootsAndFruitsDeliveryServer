package com.rootandfruit.server.api.controller;

import com.rootandfruit.server.api.controller.docs.ProductControllerDocs;
import com.rootandfruit.server.api.dto.ProductRequestDto;
import com.rootandfruit.server.api.dto.ProductResponseDto;
import com.rootandfruit.server.api.dto.ProductSailedResponseDto;
import com.rootandfruit.server.api.service.ProductService;
import java.util.List;
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
public class ProductController implements ProductControllerDocs {

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

    @DeleteMapping("product")
    public ResponseEntity<Void> deleteProduct(
            @RequestBody List<Long> productIds
    ) {
        productService.delete(productIds);
        return ResponseEntity.ok().build();
    }
}
