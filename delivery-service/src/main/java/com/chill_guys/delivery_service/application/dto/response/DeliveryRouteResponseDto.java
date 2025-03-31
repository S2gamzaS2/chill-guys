package com.chill_guys.delivery_service.application.dto.response;

import com.chill_guys.delivery_service.domain.model.DeliveryRouteStatus;
import com.chill_guys.delivery_service.domain.model.Distance;
import com.chill_guys.delivery_service.domain.model.Time;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class DeliveryRouteResponseDto {
    private Integer sequence;
    private UUID deliveryId;
    private UUID startHubId;
    private UUID endHubId;
    private Distance estimatedDistance;
    private Distance actualDistance;
    private Time estimatedTime;
    private Time actualTime;
    private DeliveryRouteStatus status;
    private  Long hubDeliveryManagerId;
    private LocalDateTime updatedAt;
}
