package com.chill_guys.hub_service.hubroute.application.dto.response;

import com.chill_guys.hub_service.hubroute.domain.entity.HubRoute;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubRouteDetailResponseDto {

  private UUID id;
  private UUID startHubId;
  private UUID endHubId;
  private String startHubName;
  private String endHubName;
  private Integer deliveryTime;
  private Double deliveryDistance;

  public static HubRouteDetailResponseDto toResponse(HubRoute hubRoute) {
    return new HubRouteDetailResponseDto(
        hubRoute.getId(),
        hubRoute.getStartHubId(),
        hubRoute.getEndHubId(),
        hubRoute.getStartHubName(),
        hubRoute.getEndHubName(),
        hubRoute.getDeliveryTime(),
        hubRoute.getDeliveryDistance()
    );
  }
}
