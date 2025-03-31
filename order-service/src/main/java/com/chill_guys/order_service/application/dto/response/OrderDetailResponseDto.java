package com.chill_guys.order_service.application.dto.response;

import com.chill_guys.order_service.domain.Order;
import lombok.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderDetailResponseDto {

    private UUID orderId;
    private UUID requestCompanyId;
    private UUID responseCompanyId;
    private String slackId;
    private String phone;
    private String address;
    private UUID deliveryId;
    private Long quantity;
    private String status;
    private String request;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;
    private Long deletedBy;
    private Timestamp deletedAt;

    public static OrderDetailResponseDto fromEntity(Order order) {
        return OrderDetailResponseDto.builder()
                .orderId(order.getOrderId())
                .requestCompanyId(order.getRequestCompanyId())
                .responseCompanyId(order.getResponseCompanyId())
                .slackId(order.getSlackId())
                .phone(order.getPhone())
                .address(order.getAddress())
                .deliveryId(order.getDeliveryId())
                .quantity(order.getQuantity())
                .status(order.getStatus().name())
                .request(order.getRequest())
                .createdBy(order.getCreatedBy())
                .createdAt(order.getCreatedAt())
                .updatedBy(order.getUpdatedBy())
                .updatedAt(order.getUpdatedAt())
                .deletedBy(order.getDeletedBy())
                .deletedAt(order.getDeletedAt())
                .build();
    }
}
