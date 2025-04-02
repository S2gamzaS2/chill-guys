package com.chill_guys.delivery_service.infrastructure.messaging.service;

import com.chill_guys.delivery_service.application.notification.NotificationService;
import com.chill_guys.delivery_service.domain.model.Delivery;
import com.chill_guys.delivery_service.infrastructure.messaging.dto.DeliveryInfoDto;
import com.chill_guys.delivery_service.infrastructure.messaging.dto.DeliveryInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackNotificationService implements NotificationService {

    private final ProducerService producerService;

    @Override
    public void sendMessage(String type, Delivery delivery, String slackId, String recipientAddress) {
        DeliveryInfoDto dto = DeliveryInfoMapper.toDto(delivery, slackId, recipientAddress);
        producerService.sendInfo(type, dto);
    }
}
