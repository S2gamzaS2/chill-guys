package com.chill_guys.slack_service.infrastructure.config.kafka;

import com.chill_guys.slack_service.application.service.SlackService;
import com.chill_guys.slack_service.infrastructure.dto.DeliveryInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerEndpoint {
    private final SlackService slackService;

    @KafkaListener(groupId = "hub", topics = "HUB")
    public void hubListen(DeliveryInfoDto dto) {
        log.info("카프카에서 받아온 HUB 메세지: " + dto.toString());
        slackService.saveSlackMessage(dto);
    }

    @KafkaListener(groupId = "company", topics = "COMPANY")
    public void listen(DeliveryInfoDto dto) {
        log.info("카프카에서 받아온 COMPANY 메세지: " + dto.toString());
        slackService.saveSlackMessage(dto);
    }
}