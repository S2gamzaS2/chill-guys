package com.chill_guys.product_service.application.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class ProductStockRequestDto {
    private UUID productId;
    private Long quantity;
}
