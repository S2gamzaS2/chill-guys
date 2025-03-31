package com.chill_guys.slack_service.application.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class SlackMessageSendRequestDto {
    private UUID id;
    private String channel;
    private String text;
}
