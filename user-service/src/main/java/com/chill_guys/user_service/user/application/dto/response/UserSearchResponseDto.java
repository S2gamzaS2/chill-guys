package com.chill_guys.user_service.user.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSearchResponseDto {

    private Long userId;
    private String username;
    private String slackId;
    private String role;

}
