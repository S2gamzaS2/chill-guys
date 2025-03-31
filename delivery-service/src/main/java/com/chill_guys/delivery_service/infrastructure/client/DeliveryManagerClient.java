package com.chill_guys.delivery_service.infrastructure.client;

import com.chill_guys.delivery_service.application.dto.response.DeliveryManagerInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name="delivery-manager-service")
public interface DeliveryManagerClient {

    @GetMapping("/api/delivery-managers/assign")
    DeliveryManagerInfoDto assignDeliveryManager(@RequestParam UUID startHubId,
                                                 @RequestParam UUID endHubId,
                                                 @RequestParam String type);
}
