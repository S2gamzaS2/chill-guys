package com.chill_guys.company_service.infrastructure.client;


import com.chill_guys.company_service.infrastructure.client.dto.request.CompanyHubRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubClient {

    @PostMapping("/api/hubs")
    UUID createDelivery(@RequestBody CompanyHubRequestDto requestDto,
                        @RequestHeader(value = "X-User-Id") String userIdHeader
    );

    @GetMapping("/api/hubs/{hubId}")
    void checkHubExists(@PathVariable("hubId") UUID hubId);
}