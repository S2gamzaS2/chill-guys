package com.chill_guys.product_service.infrastructure.client;

import com.chill_guys.product_service.infrastructure.client.dto.response.CompanyDetailResponseDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "company-service"
)
public interface CompanyClient {
    @GetMapping({"/api/companies/{id}"})
    CompanyDetailResponseDto getCompany(@PathVariable UUID id);
}
