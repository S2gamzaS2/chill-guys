package com.chill_guys.order_service.application.dto.request;

import com.chill_guys.order_service.domain.OrderStatus;
import lombok.*;

@Getter
@Builder
public class OrderUpdateRequestDto {

    private OrderStatus status;
    private Long quantity;
    private String request;
}