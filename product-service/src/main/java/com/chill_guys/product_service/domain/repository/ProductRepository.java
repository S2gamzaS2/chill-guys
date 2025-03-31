package com.chill_guys.product_service.domain.repository;

import com.chill_guys.product_service.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:companyId IS NULL OR p.companyId = :companyId) AND " +
            "(:hubId IS NULL OR p.hubId = :hubId)")
    Page<Product> searchProducts(@Param("name") String name,
                                 @Param("companyId") UUID companyId,
                                 @Param("hubId") UUID hubId,
                                 Pageable pageable);

    Optional<Product> findByName(String name);

    Product findByIdAndDeletedAtIsNull(UUID productId);
}
