package com.chill_guys.hub_service.hub.application.dto.response;

import com.chill_guys.hub_service.hub.domain.entity.Hub;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubListResponseDto {

  private Long userId;
  private String hubName;
  private String address;

  public static HubListResponseDto toResponse(Hub hub) {
    return new HubListResponseDto(
        hub.getUserId(),
        hub.getHubName(),
        hub.getAddress()
    );
  }
}
