package com.chill_guys.delivery_service.application.service;

import com.chill_guys.delivery_service.application.dto.request.DeliveryRouteUpdateRequestDto;
import com.chill_guys.delivery_service.application.dto.response.DeliveryRouteResponseDto;
import com.chill_guys.delivery_service.domain.model.Delivery;
import com.chill_guys.delivery_service.domain.model.DeliveryRouteStatus;

import java.util.List;
import java.util.UUID;

public interface DeliveryRouteService {

    void createDeliveryRoutes(Delivery delivery);

    List<DeliveryRouteResponseDto> getDeliveryRoutesList(UUID deliveryId);

    DeliveryRouteResponseDto getDeliveryRoute(UUID deliveryId, UUID routesId);

    void updateDeliveryRoute(UUID deliveryId, UUID routesId, DeliveryRouteUpdateRequestDto deliveryRouteUpdateRequestDto);

    void deleteDeliveryRoute(UUID deliveryId, UUID routesId, String userId);

    void changeDeliveryStatus(UUID deliveryId, UUID routesId, DeliveryRouteStatus status);

}
