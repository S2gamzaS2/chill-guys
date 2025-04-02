package com.chill_guys.delivery_service.application.service.impl;

import com.chill_guys.delivery_service.application.service.CompanyService;
import com.chill_guys.delivery_service.infrastructure.client.CompanyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyClient companyClient;

    @Override
    public UUID getHubIdByCompanyId(UUID companyId) {
        return companyClient.getHubIdByCompanyId(companyId);
    }
}
