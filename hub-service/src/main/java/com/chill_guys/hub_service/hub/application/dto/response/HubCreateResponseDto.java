package com.chill_guys.hub_service.hub.application.dto.response;

import com.chill_guys.hub_service.hub.domain.entity.Hub;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HubCreateResponseDto {

  private UUID id;
  private long userId;
  private String hubName;
  private String address;
  private BigDecimal latitude;
  private BigDecimal longitude;

  public HubCreateResponseDto(Hub hub) {
    this.id = hub.getId();
    this.userId = hub.getUserId();
    this.hubName = hub.getHubName();
    this.address = hub.getAddress();
    this.latitude = hub.getLatitude();
    this.longitude = hub.getLongitude();
  }
}
