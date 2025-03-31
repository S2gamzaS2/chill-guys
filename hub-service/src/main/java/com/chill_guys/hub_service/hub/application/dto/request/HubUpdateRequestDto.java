package com.chill_guys.hub_service.hub.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HubUpdateRequestDto {

  @NotNull(message = "허브 관리자 지정은 필수 입력 사항입니다.")
  private Long userId;

  @NotBlank(message = "허브 이름은 필수 입력 사항입니다.")
  private String hubName;

  @NotBlank(message = "주소는 필수 입력 사항입니다.")
  private String address;

  @NotNull(message = "위도는 필수 입력 사항입니다.")
  private BigDecimal latitude;

  @NotNull(message = "경도는 필수 입력 사항입니다.")
  private BigDecimal longitude;


}
