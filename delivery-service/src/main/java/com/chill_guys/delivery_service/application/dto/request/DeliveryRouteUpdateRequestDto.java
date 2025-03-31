package com.chill_guys.delivery_service.application.dto.request;

import com.chill_guys.delivery_service.domain.model.Distance;
import com.chill_guys.delivery_service.domain.model.Time;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeliveryRouteUpdateRequestDto {
    private UUID startHubId;
    private UUID endHubId;
    private Distance estimatedDistance;
    private Distance actualDistance;
    private Time estimatedTime;
    private Time actualTime;
    private Long hubDeliveryManagerId;
}
