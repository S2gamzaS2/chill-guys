package com.chill_guys.user_service.user.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRoleUpdateResponseDto {

    private Long userId;
    private String slackId;
    private String newRole;
}
