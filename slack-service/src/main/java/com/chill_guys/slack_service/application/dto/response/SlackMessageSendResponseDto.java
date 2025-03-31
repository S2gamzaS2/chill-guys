package com.chill_guys.slack_service.application.dto.response;

import lombok.*;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
public class SlackMessageSendResponseDto {
    private UUID id;
    private String slackId;
    private String result;
    private Timestamp createdAt;
    private Long createdBy;
}
