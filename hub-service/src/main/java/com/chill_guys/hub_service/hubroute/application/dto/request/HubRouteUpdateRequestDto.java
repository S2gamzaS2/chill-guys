package com.chill_guys.hub_service.hubroute.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubRouteUpdateRequestDto {

  @NotNull(message = "출발 허브 ID는 필수입니다.")
  private UUID startHubId;

  @NotNull(message = "도착 허브 ID는 필수입니다.")
  private UUID endHubId;

  @NotBlank(message = "출발 허브 이름은 필수입니다.")
  private String startHubName;

  @NotBlank(message = "도착 허브 이름은 필수입니다.")
  private String endHubName;

  @NotNull(message = "배송 시간은 필수입니다.")
  @Positive(message = "배송 시간은 양수여야 합니다.")
  private Integer deliveryTime;

  @NotNull(message = "배송 거리는 필수입니다.")
  @Positive(message = "배송 거리는 양수여야 합니다.")
  private Double deliveryDistance;
}
