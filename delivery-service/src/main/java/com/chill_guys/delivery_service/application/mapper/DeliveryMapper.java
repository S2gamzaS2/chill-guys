package com.chill_guys.delivery_service.application.mapper;

import com.chill_guys.delivery_service.application.dto.request.OrderDeliveryRequestDto;
import com.chill_guys.delivery_service.application.dto.response.DeliveryResponseDto;
import com.chill_guys.delivery_service.domain.model.Delivery;
import com.chill_guys.delivery_service.domain.model.RecipientCompany;

import java.util.UUID;

public class DeliveryMapper {

    public static Delivery toEntity(OrderDeliveryRequestDto dto,
                                    UUID departureHubId,
                                    UUID destinationHubId){
        RecipientCompany recipientCompany = RecipientCompany.builder()
                .companyId(dto.getRequestCompanyId())
                .address(dto.getAddress())
                .slackId(dto.getSlackId())
                .phone(dto.getPhone())
                .build();

        return Delivery.builder()
                .orderId(dto.getOrderId())
                .departureHubId(departureHubId)
                .destinationHubId(destinationHubId)
                .productId(dto.getProductId())
                .recipientCompany(recipientCompany)
                .build();
    }

    public static DeliveryResponseDto toDto (Delivery delivery){
        return DeliveryResponseDto.builder()
                .orderId(delivery.getOrderId())
                .status(delivery.getDeliveryStatus())
                .departureHubId(delivery.getDepartureHubId())
                .destinationHubId(delivery.getDestinationHubId())
                .productId(delivery.getProductId())
                .recipientCompany(delivery.getRecipientCompany())
                .createdAt(delivery.getCreatedAt())
                .deliveryManagerId(delivery.getDeliveryManagerId())
                .build();
    }
}
