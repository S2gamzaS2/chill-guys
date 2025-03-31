package com.chill_guys.product_service.application.dto.response;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
public class ProductGetResponseDto {
    private UUID id;
    private UUID companyId;
    private UUID hubId;
    private String name;
    private Long stock;
    private Timestamp createdAt;
    private Long createdBy;
    private Timestamp updatedAt;
    private Long updatedBy;
}
