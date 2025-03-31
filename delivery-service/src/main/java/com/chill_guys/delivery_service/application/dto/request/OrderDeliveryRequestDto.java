package com.chill_guys.delivery_service.application.dto.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderDeliveryRequestDto {
    private UUID orderId;
    private UUID requestCompanyId;
    private UUID responseCompanyId;
    private UUID productId;
    private String slackId;
    private String phone;
    private String address;
}
