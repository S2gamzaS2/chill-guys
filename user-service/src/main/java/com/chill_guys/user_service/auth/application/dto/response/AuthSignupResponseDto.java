package com.chill_guys.user_service.auth.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthSignupResponseDto {

    private String username;
    private String slackId;

}
