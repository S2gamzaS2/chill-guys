package com.chill_guys.hub_service.hubroute.application.dto.response;

import com.chill_guys.hub_service.hubroute.domain.entity.HubRoute;
import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubRouteListResponseDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private UUID startHubId;
  private UUID endHubId;
  private String startHubName;
  private String endHubName;
  private Integer deliveryTime;
  private Double deliveryDistance;

  public static HubRouteListResponseDto toResponse(HubRoute hubRoute) {
    return new HubRouteListResponseDto(
        hubRoute.getStartHubId(),
        hubRoute.getEndHubId(),
        hubRoute.getStartHubName(),
        hubRoute.getEndHubName(),
        hubRoute.getDeliveryTime(),
        hubRoute.getDeliveryDistance()
    );
  }
}
