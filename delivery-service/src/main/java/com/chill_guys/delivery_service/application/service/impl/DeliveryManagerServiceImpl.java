package com.chill_guys.delivery_service.application.service.impl;

import com.chill_guys.delivery_service.application.dto.response.DeliveryManagerInfoDto;
import com.chill_guys.delivery_service.application.service.DeliveryManagerService;
import com.chill_guys.delivery_service.infrastructure.client.DeliveryManagerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryManagerServiceImpl implements DeliveryManagerService {

    private final DeliveryManagerClient deliveryManagerClient;

    @Override
    public DeliveryManagerInfoDto assignDeliveryManager(UUID startHubId, UUID endHubId, String type) {
        return deliveryManagerClient.assignDeliveryManager(startHubId, endHubId, type);
    }
}
