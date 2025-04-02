package com.chill_guys.delivery_service.application.service;

import com.chill_guys.delivery_service.application.dto.response.DeliveryManagerInfoDto;

import java.util.UUID;

public interface DeliveryManagerService {

    DeliveryManagerInfoDto assignDeliveryManager(UUID startHubId,
                                                 UUID endHubId,
                                                 String type);
}
