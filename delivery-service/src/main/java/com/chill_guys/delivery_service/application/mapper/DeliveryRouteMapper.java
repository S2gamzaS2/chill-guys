package com.chill_guys.delivery_service.application.mapper;

import com.chill_guys.delivery_service.application.dto.response.DeliveryRouteResponseDto;
import com.chill_guys.delivery_service.domain.model.DeliveryRoute;
import com.chill_guys.delivery_service.domain.model.Distance;
import com.chill_guys.delivery_service.domain.model.Time;
import com.chill_guys.delivery_service.domain.model.*;

import java.util.UUID;

public class DeliveryRouteMapper {

    public static DeliveryRoute toEntity(UUID deliveryId, Integer sequence, UUID startHubId, UUID endHubId, Double estimatedDistance, Integer estimatedTime, Double actualDistance, Integer actualTime) {
        Distance estimatedDis = new Distance(estimatedDistance);
        Distance actualDis = new Distance(actualDistance);
        Time estimatedTi = new Time(estimatedTime);
        Time actualTi = new Time(actualTime);

        return DeliveryRoute.builder()
                .deliveryId(deliveryId)
                .sequence(sequence)
                .startHudId(startHubId)
                .endHudId(endHubId)
                .estimatedDistance(estimatedDis)
                .actualDistance(actualDis)
                .estimatedTime(estimatedTi)
                .actualTime(actualTi)
                .build();
    }

    public static DeliveryRouteResponseDto toDto(DeliveryRoute deliveryRoute) {
        return DeliveryRouteResponseDto.builder()
                .sequence(deliveryRoute.getSequence())
                .deliveryId(deliveryRoute.getDeliveryId())
                .startHubId(deliveryRoute.getStartHudId())
                .endHubId(deliveryRoute.getEndHudId())
                .estimatedDistance(deliveryRoute.getEstimatedDistance())
                .actualDistance(deliveryRoute.getActualDistance())
                .estimatedTime(deliveryRoute.getEstimatedTime())
                .actualTime(deliveryRoute.getActualTime())
                .status(deliveryRoute.getStatus())
                .hubDeliveryManagerId(deliveryRoute.getHubDeliveryManagerId())
                .updatedAt(deliveryRoute.getUpdatedAt())
                .build();
    }
}
