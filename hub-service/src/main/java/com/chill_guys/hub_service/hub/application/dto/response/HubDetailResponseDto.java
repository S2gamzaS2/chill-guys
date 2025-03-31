package com.chill_guys.hub_service.hub.application.dto.response;

import com.chill_guys.hub_service.hub.domain.entity.Hub;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubDetailResponseDto {

  private UUID id;
  private Long userId;
  private String hubName;
  private String address;
  private BigDecimal latitude;
  private BigDecimal longitude;

  public static HubDetailResponseDto toResponse(Hub hub) {
    return new HubDetailResponseDto(
        hub.getId(),
        hub.getUserId(),
        hub.getHubName(),
        hub.getAddress(),
        hub.getLatitude(),
        hub.getLongitude()
    );
  }
}
