package com.chill_guys.slack_service.infrastructure.client;

import com.chill_guys.slack_service.infrastructure.dto.OrderSlackDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "order-service")
public interface OrderClient {
    @GetMapping("/api/orders/{id}/info")
    OrderSlackDto getOrderInfo(@PathVariable UUID id);
}
