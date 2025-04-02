package com.chill_guys.delivery_service.application.service.impl;

import com.chill_guys.delivery_service.application.service.ProductService;
import com.chill_guys.delivery_service.infrastructure.client.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductClient productClient;

    @Override
    public UUID getHubIdByProductId(UUID productId) {
        return productClient.getHubIdByProductId(productId);
    }
}
