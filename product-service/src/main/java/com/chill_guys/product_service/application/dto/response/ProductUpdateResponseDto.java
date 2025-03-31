package com.chill_guys.product_service.application.dto.response;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Builder
public class ProductUpdateResponseDto {
    private UUID id;
    private UUID companyId;
    private UUID hubId;
    private String name;
    private Long stock;
    private Timestamp updatedAt;
    private Long updatedBy;
}
