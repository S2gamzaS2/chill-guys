package com.chill_guys.user_service.user.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryManagerUpdateRequestDto {
    private String slackId;
}
