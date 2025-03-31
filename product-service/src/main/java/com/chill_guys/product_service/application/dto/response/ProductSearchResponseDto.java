package com.chill_guys.product_service.application.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class ProductSearchResponseDto {
    private List<ProductGetResponseDto> products;
    private int page;
    private int size;
}
