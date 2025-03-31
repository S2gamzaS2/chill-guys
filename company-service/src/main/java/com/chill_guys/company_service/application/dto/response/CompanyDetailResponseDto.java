package com.chill_guys.company_service.application.dto.response;

import com.chill_guys.company_service.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDetailResponseDto {

    private UUID id;
    private UUID hubId;
    private String name;
    private String type;
    private String address;
    private String phone;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;
    private Long deletedBy;
    private Timestamp deletedAt;

    public static CompanyDetailResponseDto fromEntity(Company company) {
        return CompanyDetailResponseDto.builder()
                .id(company.getId())
                .hubId(company.getHubId().getHubId())
                .name(company.getName())
                .type(company.getType().name())
                .address(company.getAddress())
                .phone(company.getPhone())
                .createdBy(company.getCreatedBy())
                .createdAt(company.getCreatedAt())
                .updatedBy(company.getUpdatedBy())
                .updatedAt(company.getUpdatedAt())
                .deletedBy(company.getDeletedBy())
                .deletedAt(company.getDeletedAt())
                .build();
    }
}
