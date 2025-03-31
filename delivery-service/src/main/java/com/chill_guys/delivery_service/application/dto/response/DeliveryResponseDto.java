package com.chill_guys.delivery_service.application.dto.response;

import com.chill_guys.delivery_service.domain.model.DeliveryStatus;
import com.chill_guys.delivery_service.domain.model.RecipientCompany;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class DeliveryResponseDto {
    private UUID orderId;
    private DeliveryStatus status;
    private UUID departureHubId;
    private UUID destinationHubId;
    private UUID productId;
    private RecipientCompany recipientCompany;
    private LocalDateTime createdAt;
    private Long deliveryManagerId;
}
