package com.chill_guys.order_service.domain;

import com.chill_guys.order_service.application.dto.request.OrderCreateRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "p_order")
public class Order {

    @Id
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "request_company_id", nullable = false)
    private UUID requestCompanyId;

    @Column(name = "response_company_id", nullable = false)
    private UUID responseCompanyId;

    @Column(name = "slack_id")
    private String slackId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "request", nullable = false)
    private String request;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public static Order toEntity(UUID orderId, OrderCreateRequestDto dto, UUID deliveryId, Long userId) {
        return Order.builder()
                .orderId(orderId)
                .deliveryId(deliveryId)
                .productId(dto.getProductId())
                .requestCompanyId(dto.getRequestCompanyId())
                .responseCompanyId(dto.getResponseCompanyId())
                .slackId(dto.getSlackId())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .status(OrderStatus.PENDING)
                .quantity(dto.getQuantity())
                .request(dto.getRequest())
                .createdBy(userId)
                .createdAt(Timestamp.from(Instant.now()))
                .updatedBy(userId)
                .updatedAt(Timestamp.from(Instant.now()))
                .deletedBy(null)
                .deletedAt(null)
                .build();
    }

    public void deleteOrder(Long deletedBy) {
        this.deletedAt = Timestamp.from(Instant.now());
        this.deletedBy = deletedBy;
    }

    public void updateOrder(OrderStatus status, Long quantity, String request, Long updatedBy) {
        this.status = status;
        this.quantity = quantity;
        this.request = request;
        this.updatedBy = updatedBy;
        this.updatedAt = Timestamp.from(Instant.now());
    }

    // 주문 상태만 변경하는 메서드
    public void updateStatus(OrderStatus newStatus, Long updatedBy) {
        this.status = newStatus;
        this.updatedBy = updatedBy;
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
