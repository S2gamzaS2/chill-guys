package com.chill_guys.delivery_service.infrastructure.messaging.dto;

import com.chill_guys.delivery_service.domain.model.Delivery;

public class DeliveryInfoMapper {

    public static DeliveryInfoDto toDto(Delivery delivery, String slackId, String address) {
        return DeliveryInfoDto.builder()
                .departureHubId(delivery.getDepartureHubId())
                .destinationHubId(delivery.getDestinationHubId())
                .deliveryManagerId(delivery.getDeliveryManagerId())
                .productId(delivery.getProductId())
                .slackId(slackId)
                .orderId(delivery.getOrderId())
                .address(address)
                .build();
    }
}