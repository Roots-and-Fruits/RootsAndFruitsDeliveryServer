package com.rootandfruit.server.api.repository;

import com.rootandfruit.server.api.domain.Product;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductById(Long id);

    default Product findProductByIdOrThrow(Long id) {
        return findProductById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PRODUCT_ERROR));
    }

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false")
    List<Product> findAllActiveProducts();

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false and p.isSailed = true")
    List<Product> findSailedProducts();

    @Modifying
    @Query("UPDATE Product p SET p.sequence = p.sequence - 1 WHERE p.sequence > :currentSequence AND p.sequence <= :newSequence")
    void decrementSequenceRange(@Param("currentSequence") int currentSequence, @Param("newSequence") int newSequence);

    @Modifying
    @Query("UPDATE Product p SET p.sequence = p.sequence + 1 WHERE p.sequence < :currentSequence AND p.sequence >= :newSequence")
    void incrementSequenceRange(@Param("currentSequence") int currentSequence, @Param("newSequence") int newSequence);

    @Query("SELECT COALESCE(MAX(p.sequence), 0) FROM Product p WHERE p.isTrial = :isTrial")
    int findMaxSequenceByIsTrial(@Param("isTrial") boolean isTrial);
}
