package com.chill_guys.slack_service.infrastructure.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class HubResponseDto {
    private UUID id;
    private Long userId;
    private String hubName;
    private String address;
    private Double latitude;
    private Double longitude;
}
