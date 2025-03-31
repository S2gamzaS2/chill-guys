package com.chill_guys.hub_service.hubroute.application.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HubRouteCreateRequestDto {

  @NotNull(message = "출발 허브 ID는 필수입니다.")
  private UUID startHubId;

  @NotNull(message = "도착 허브 ID는 필수입니다.")
  private UUID endHubId;
}
