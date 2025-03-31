package com.chill_guys.company_service.application.dto.request;

import com.chill_guys.company_service.domain.CompanyType;
import lombok.*;

@Getter
@Builder
public class CompanyUpdateRequestDto {

    private String name;
    private CompanyType type;
    private String address;
    private String phone;
}


