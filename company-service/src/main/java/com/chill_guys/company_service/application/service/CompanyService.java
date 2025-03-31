package com.chill_guys.company_service.application.service;

import com.chill_guys.company_service.application.dto.request.CompanyCreateRequestDto;
import com.chill_guys.company_service.application.dto.request.CompanyUpdateRequestDto;
import com.chill_guys.company_service.application.dto.response.CompanyDetailResponseDto;
import com.chill_guys.company_service.domain.Company;
import com.chill_guys.company_service.domain.HubId;
import com.chill_guys.company_service.infrastructure.client.HubClient;
import com.chill_guys.company_service.repository.CompanyRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final HubClient hubClient;

    // [배송에서 아이디 가져오기]
    public UUID getHubIdByCompanyId(UUID companyId) {
        Company company = companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (company.getHubId() == null) {
            throw new RuntimeException("해당 회사는 허브 정보가 없습니다.");
        }

        return company.getHubId().getHubId();
    }


    // [생성]
    @Transactional
    public CompanyDetailResponseDto createCompany(CompanyCreateRequestDto requestDto, String userIdHeader) {
        Long userId = Long.parseLong(userIdHeader);

        // 허브 존재 여부 검증
        try {
            hubClient.checkHubExists(requestDto.getHubId());
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new RuntimeException("허브가 존재하지 않습니다.");
            }
            throw new RuntimeException("허브 서비스 호출 중 오류가 발생했습니다.");
        }

        Company company = Company.create(requestDto, new HubId(requestDto.getHubId()), userId);
        companyRepository.save(company);
        return CompanyDetailResponseDto.fromEntity(company);
    }

    // [개별 조회]
    @Transactional(readOnly = true)
    public CompanyDetailResponseDto getCompanyById(UUID id) {
        log.info("회사 조회 시도 - ID: {}", id);
        return companyRepository.findByIdAndDeletedAtIsNull(id)
                .map(CompanyDetailResponseDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    // [전체 조회]
    @Transactional(readOnly = true)
    public Page<CompanyDetailResponseDto> getAllCompanies(Pageable pageable) {
        return companyRepository.findAllByDeletedAtIsNull(pageable)
                .map(CompanyDetailResponseDto::fromEntity);
    }

    // [수정]
    @Transactional
    public CompanyDetailResponseDto updateCompany(UUID id, CompanyUpdateRequestDto updateDto, String userIdHeader) {
        Company company = companyRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Long userId = Long.parseLong(userIdHeader);

        company.updateCompany(updateDto, userId);
        return CompanyDetailResponseDto.fromEntity(company);
    }

    // [삭제]
    @Transactional
    public void deleteCompany(UUID id, String userIdHeader) {
        Company company = companyRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Long userId = Long.parseLong(userIdHeader);

        company.deleteCompany(userId);
    }
}
