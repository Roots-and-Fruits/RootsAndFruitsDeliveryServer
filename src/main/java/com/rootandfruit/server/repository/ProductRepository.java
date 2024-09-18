package com.rootandfruit.server.repository;

import com.rootandfruit.server.domain.Product;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductById(Long id);

    default Product findProductByIdOrThrow(Long id) {
        return findProductById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PRODUCT_ERROR));
    }
}
