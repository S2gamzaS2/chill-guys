package com.chill_guys.user_service.user.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateResponseDto {
    private Long userId;
    private String username;
    private String slackId;

}
