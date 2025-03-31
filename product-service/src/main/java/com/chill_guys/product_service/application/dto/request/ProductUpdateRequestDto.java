package com.chill_guys.product_service.application.dto.request;

import lombok.*;

@Getter
@Builder
public class ProductUpdateRequestDto {
    private String name;
    private Long stock;
}
