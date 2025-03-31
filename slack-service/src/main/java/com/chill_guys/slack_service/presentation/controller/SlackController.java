package com.chill_guys.slack_service.presentation.controller;

import com.chill_guys.slack_service.application.dto.response.SlackMessageSaveResponseDto;
import com.chill_guys.slack_service.application.dto.response.SlackMessageSendResponseDto;
import com.chill_guys.slack_service.application.service.SlackService;
import com.chill_guys.slack_service.infrastructure.dto.DeliveryInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/slack")
public class SlackController {

    private final SlackService slackService;

    public SlackController(SlackService slackService) {
        this.slackService = slackService;
    }

    @PostMapping("/save")
    public SlackMessageSaveResponseDto saveMessage(@RequestBody DeliveryInfoDto dto) {
        return slackService.saveSlackMessage(dto);
    }

    @PostMapping("/send/{id}/{slack_id}")
    public ResponseEntity<SlackMessageSendResponseDto> sendMessage(@PathVariable("id") UUID id,
                                            @PathVariable("slack_id") String slackId
    ) {
        slackService.sendSlackMessage(id, slackId);
        return ResponseEntity.ok().build();
    }
}