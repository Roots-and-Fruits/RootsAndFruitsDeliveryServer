package com.rootandfruit.server.service;

import com.rootandfruit.server.domain.Product;
import com.rootandfruit.server.dto.ProductRequestDto;
import com.rootandfruit.server.dto.ProductResponseDto;
import com.rootandfruit.server.dto.ProductSailedResponseDto;
import com.rootandfruit.server.dto.ProductTmpDto;
import com.rootandfruit.server.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductResponseDto getAllProducts() {
        List<Product> products = productRepository.findAllActiveProducts();

        List<ProductTmpDto> trialProducts = products.stream()
                .filter(Product::isTrial)
                .map(this::toProductTmpDto)
                .toList();

        List<ProductTmpDto> sailedProducts = products.stream()
                .filter(product -> !product.isTrial())
                .map(this::toProductTmpDto)
                .toList();

        return ProductResponseDto.of(trialProducts, sailedProducts);
    }

    @Transactional(readOnly = true)
    public ProductSailedResponseDto getSailedProducts() {
        List<Product> products = productRepository.findSailedProducts();

        List<ProductTmpDto> sailedProducts = products.stream()
                .map(this::toProductTmpDto)
                .toList();
        return ProductSailedResponseDto.of(sailedProducts);
    }

    private ProductTmpDto toProductTmpDto(Product product) {
        return ProductTmpDto.of(
                product.getId(),
                product.getProductName(),
                product.getPrice()
        );
    }

    @Transactional
    public void changeProductSailedStatus(Long productId) {
        Product product = productRepository.findProductByIdOrThrow(productId);

        product.switchSailedStatus(product.isSailed());
    }

    public void createNewProduct(ProductRequestDto productRequestDto) {
        productRepository.save(
                Product.createProduct(
                        productRequestDto.productName(),
                        productRequestDto.productPrice(),
                        productRequestDto.isTrial(),
                        false,
                        false
                )
        );
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findProductByIdOrThrow(productId);

        productRepository.delete(product);
    }
}
