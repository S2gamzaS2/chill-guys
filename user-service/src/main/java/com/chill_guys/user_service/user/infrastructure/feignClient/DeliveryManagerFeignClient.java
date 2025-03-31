package com.chill_guys.user_service.user.infrastructure.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "delivery-manager-service")
public interface DeliveryManagerFeignClient {

    @PostMapping("/api/delivery-managers")
    ResponseEntity<Void> createDeliveryManager(@RequestParam("id") Long id,
                                               @RequestParam("slackId") String slackId
    );

    @PutMapping("/api/delivery-managers/update-slack-id/{delivery_manager_id}")
    void updateDeliveryManager(@PathVariable("delivery_manager_id") Long deliveryManagerId,
                               @RequestParam String slackId
    );

}
