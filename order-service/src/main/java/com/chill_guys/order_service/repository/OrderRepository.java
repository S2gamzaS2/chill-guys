package com.chill_guys.order_service.repository;

import com.chill_guys.order_service.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<Order> findByOrderIdAndDeletedAtIsNull(UUID orderId);
}
