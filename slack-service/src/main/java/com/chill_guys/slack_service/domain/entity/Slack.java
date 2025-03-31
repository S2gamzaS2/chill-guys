package com.chill_guys.slack_service.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "p_slack")
public class Slack {
    @Id
    private UUID id;
    private String slackId;
    private String text;
    private Timestamp createdAt;
    private Long createdBy;
}
