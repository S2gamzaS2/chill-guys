package com.chill_guys.slack_service.infrastructure.client;

import com.chill_guys.slack_service.infrastructure.dto.HubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub-service")
public interface HubClient {
    @GetMapping("/api/hubs/{hubId}")
    HubResponseDto getAddressByHubId(@PathVariable("hubId") UUID hubId);
}