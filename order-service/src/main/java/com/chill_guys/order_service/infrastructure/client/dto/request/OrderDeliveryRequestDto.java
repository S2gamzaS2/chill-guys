package com.chill_guys.order_service.infrastructure.client.dto.request;

import com.chill_guys.order_service.application.dto.request.OrderCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDeliveryRequestDto {

    private UUID orderId;
    private UUID productId;
    private UUID requestCompanyId;
    private UUID responseCompanyId;
    private String slackId;
    private String phone;
    private String address;

    public static OrderDeliveryRequestDto fromOrderRequest(OrderCreateRequestDto requestDto, UUID orderId) {
        return OrderDeliveryRequestDto.builder()
                .orderId(orderId)
                .productId(requestDto.getProductId())
                .requestCompanyId(requestDto.getRequestCompanyId())
                .responseCompanyId(requestDto.getResponseCompanyId())
                .slackId(requestDto.getSlackId())
                .phone(requestDto.getPhone())
                .address(requestDto.getAddress())
                .build();
    }
}
