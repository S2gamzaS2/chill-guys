package com.chill_guys.order_service.infrastructure.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStockRequestDto {
    private UUID productId;
    private Long quantity;
}

