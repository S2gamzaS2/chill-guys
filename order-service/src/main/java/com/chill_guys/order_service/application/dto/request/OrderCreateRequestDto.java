package com.chill_guys.order_service.application.dto.request;

import com.chill_guys.order_service.domain.OrderStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class OrderCreateRequestDto {

    private UUID productId;
    private UUID requestCompanyId;
    private UUID responseCompanyId;
    private String slackId;
    private String phone;
    private String address;
    private OrderStatus status;
    private Long quantity;
    private String request;
}
