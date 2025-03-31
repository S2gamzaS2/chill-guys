package com.chill_guys.delivery_service.application.service;

import com.chill_guys.delivery_service.infrastructure.messaging.dto.DeliveryInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final KafkaTemplate<String, DeliveryInfoDto> kafkaTemplate;

    public void sendInfo(String topic, DeliveryInfoDto dto) {
        kafkaTemplate.send(topic, dto);
    }
}
