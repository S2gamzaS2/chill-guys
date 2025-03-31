package com.chill_guys.delivery_service.application.dto.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DeliveryUpdateRequestDto {
    private UUID departureHubId;
    private UUID destinationHubId;
    private UUID productId;
    private UUID companyId;
    private String address;
    private String slackId;
    private String phoneNumber;
    private Long deliveryManagerId;
}
