package com.chill_guys.order_service.infrastructure.client;

import com.chill_guys.order_service.infrastructure.client.dto.request.OrderDeliveryRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "delivery-service")
public interface DeliveryClient {

    @PostMapping("/api/deliveries")
    UUID createDelivery(@RequestBody OrderDeliveryRequestDto requestDto,
                        @RequestHeader(value = "X-User-Id") String userIdHeader
    );
}