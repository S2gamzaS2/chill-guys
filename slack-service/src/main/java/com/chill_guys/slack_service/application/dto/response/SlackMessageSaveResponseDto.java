package com.chill_guys.slack_service.application.dto.response;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
public class SlackMessageSaveResponseDto {
    private UUID id;
    private String text;
    private String slackId;
    private Timestamp createdAt;
    private Long createdBy;
}
