package com.chill_guys.delivery_service.application.service;

import com.chill_guys.delivery_service.application.dto.request.DeliveryUpdateRequestDto;
import com.chill_guys.delivery_service.application.dto.request.OrderDeliveryRequestDto;
import com.chill_guys.delivery_service.application.dto.response.DeliveryResponseDto;
import com.chill_guys.delivery_service.domain.model.DeliveryStatus;

import java.util.List;
import java.util.UUID;

public interface DeliveryService {

    UUID createDelivery(OrderDeliveryRequestDto orderDeliveryRequestDto);

    List<DeliveryResponseDto> getDeliveryList();

    DeliveryResponseDto getDelivery(UUID deliveryId);

    void updateDelivery(UUID deliveryId, DeliveryUpdateRequestDto deliveryUpdateRequestDto);

    void deleteDelivery(UUID deliveryId, String userId);

    void assignPendingDeliveries();

    void changeDeliveryStatus(UUID deliveryId, DeliveryStatus deliveryStatus);

}
