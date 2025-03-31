package com.chill_guys.hub_service.hub.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRoleSearchResponseDto {

  private Long userId;
  private String username;
  private String slackId;
  private String role;
}
