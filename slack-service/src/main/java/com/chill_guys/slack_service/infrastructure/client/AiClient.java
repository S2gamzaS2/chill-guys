package com.chill_guys.slack_service.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-service")
public interface AiClient {
    @PostMapping("/api/ai/generate")
    String generateCompletion(@RequestBody String inputForAI);
}
