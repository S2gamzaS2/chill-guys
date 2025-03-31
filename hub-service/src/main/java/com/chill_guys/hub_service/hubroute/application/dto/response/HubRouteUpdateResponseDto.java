package com.chill_guys.hub_service.hubroute.application.dto.response;

import com.chill_guys.hub_service.hubroute.domain.entity.HubRoute;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HubRouteUpdateResponseDto {

  private UUID Id;
  private UUID startHubId;
  private UUID endHubId;
  private String startHubName;
  private String endHubName;
  private Integer deliveryTime;
  private Double deliveryDistance;


  public HubRouteUpdateResponseDto(HubRoute updateHubRoute) {
    this.Id = updateHubRoute.getId();
    this.startHubId = updateHubRoute.getStartHubId();
    this.endHubId = updateHubRoute.getEndHubId();
    this.startHubName = updateHubRoute.getStartHubName();
    this.endHubName = updateHubRoute.getEndHubName();
    this.deliveryTime = updateHubRoute.getDeliveryTime();
    this.deliveryDistance = updateHubRoute.getDeliveryDistance();
  }
}
