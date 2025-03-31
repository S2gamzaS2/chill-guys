package com.chill_guys.delivery_service.domain.repository;

import com.chill_guys.delivery_service.domain.model.DeliveryManager;
import com.chill_guys.delivery_service.domain.model.DeliveryManagerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryManagerRepository extends JpaRepository<DeliveryManager, Long> {
    List<DeliveryManager> findByAndDeletedAtIsNull();

    Optional<DeliveryManager> findByIdAndDeletedAtIsNull(Long deliveryManagerId);

    @Query("SELECT COALESCE(MAX(d.sequence), 0) FROM DeliveryManager d")
    Long getMaxSequence();

    @Query("SELECT d FROM DeliveryManager d " +
            "WHERE d.hubId = :hubId AND d.type = :type AND d.deletedAt IS NULL " +
            "ORDER BY d.sequence ASC")
    List<DeliveryManager> findByHubIdAndTypeAndDeletedAtIsNullOrdered(@Param("hubId") UUID hubId,
                                                                      @Param("type") DeliveryManagerType type);
    @Query("SELECT MAX(d.sequence) FROM DeliveryManager d")
    Long findMaxSequence();
}
