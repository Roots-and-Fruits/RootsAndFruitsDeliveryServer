package com.rootandfruit.server.api.service;

import com.rootandfruit.server.api.domain.Product;
import com.rootandfruit.server.api.dto.ProductAllDto;
import com.rootandfruit.server.api.dto.ProductRequestDto;
import com.rootandfruit.server.api.dto.ProductResponseDto;
import com.rootandfruit.server.api.dto.ProductSailedResponseDto;
import com.rootandfruit.server.api.dto.ProductTmpDto;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import com.rootandfruit.server.api.repository.ProductRepository;
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

        List<ProductAllDto> trialProducts = products.stream()
                .filter(Product::isTrial)
                .map(this::toProductAllDto)
                .toList();

        List<ProductAllDto> sailedProducts = products.stream()
                .filter(product -> !product.isTrial())
                .map(this::toProductAllDto)
                .toList();

        return ProductResponseDto.of(trialProducts, sailedProducts);
    }

    @Transactional(readOnly = true)
    public ProductSailedResponseDto getSailedProducts() {
        List<Product> products = productRepository.findSailedProducts();

        List<ProductTmpDto> trialSailedProducts = products.stream()
                .filter(Product::isTrial)
                .map(this::toProductTmpDto)
                .toList();

        List<ProductTmpDto> sailedProducts = products.stream()
                .filter(product -> !product.isTrial())
                .map(this::toProductTmpDto)
                .toList();

        return ProductSailedResponseDto.of(trialSailedProducts, sailedProducts);
    }

    private ProductTmpDto toProductTmpDto(Product product) {
        return ProductTmpDto.of(
                product.getId(),
                product.getProductName(),
                product.getPrice()
        );
    }

    private ProductAllDto toProductAllDto(Product product) {
        return ProductAllDto.of(
                product.getId(),
                product.getProductName(),
                product.getPrice(),
                product.isSailed()
        );
    }

    @Transactional
    public void changeProductSailedStatus(Long productId) {
        Product product = productRepository.findProductByIdOrThrow(productId);
        if (product.isDeleted()) {
            throw new CustomException(ErrorType.ALREADY_DELETED_PRODUCT);
        }
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
    public void delete(List<Long> productIds) {
        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_PRODUCT_ERROR);
        }
        products.forEach(Product::deleteProduct);
    }
}
