package com.chill_guys.product_service.infrastructure.client;

import com.chill_guys.product_service.infrastructure.client.dto.response.HubDetailResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "hub-service"
)
public interface HubClient {
    @GetMapping({"/api/hubs/{hubId}"})
    HubDetailResponseDto getHub(@PathVariable("hubId") UUID id);
}
