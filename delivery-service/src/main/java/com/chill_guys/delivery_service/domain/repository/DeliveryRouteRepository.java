package com.chill_guys.delivery_service.domain.repository;

import com.chill_guys.delivery_service.domain.model.DeliveryRoute;
import com.chill_guys.delivery_service.domain.model.DeliveryRouteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {

    List<DeliveryRoute> findByDeliveryIdAndDeletedAtIsNull(UUID deliveryId);

    Optional<DeliveryRoute> findByIdAndDeliveryIdAndDeletedAtIsNull(UUID routesId, UUID deliveryId);

    List<DeliveryRoute> findByStatusAndDeletedAtIsNull(DeliveryRouteStatus deliveryStatus);
}
