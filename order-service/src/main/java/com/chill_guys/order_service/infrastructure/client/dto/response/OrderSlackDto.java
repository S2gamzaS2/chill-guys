package com.chill_guys.order_service.infrastructure.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderSlackDto {
    private Long quantity;
    private String request;
}
