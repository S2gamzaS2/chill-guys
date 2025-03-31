package com.chill_guys.delivery_service.application.dto.response;

import lombok.Getter;

import java.util.UUID;

@Getter
public class HubRouteListResponseDto {
    private UUID startHubId;
    private UUID endHubId;
    private Integer deliveryTime;
    private Double deliveryDistance;
}
