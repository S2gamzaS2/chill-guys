package com.chill_guys.company_service.infrastructure.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyHubRequestDto {

    private UUID requestCompanyId;
    private UUID responseCompanyId;

    public static CompanyHubRequestDto fromOrderRequest(CompanyHubRequestDto requestDto, UUID orderId) {
        return CompanyHubRequestDto.builder()
                .requestCompanyId(requestDto.getRequestCompanyId())
                .responseCompanyId(requestDto.getResponseCompanyId())
                .build();
    }
}
