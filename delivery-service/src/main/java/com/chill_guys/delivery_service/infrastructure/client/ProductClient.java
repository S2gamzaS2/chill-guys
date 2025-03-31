package com.chill_guys.delivery_service.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="product-service")
public interface ProductClient {

    @GetMapping("/api/products/{productId}/hub")
    UUID getHubIdByProductId(@PathVariable("productId") UUID productId);

}
