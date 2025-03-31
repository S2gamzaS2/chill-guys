package com.chill_guys.company_service.presentation.controller;

import com.chill_guys.company_service.application.dto.request.CompanyCreateRequestDto;
import com.chill_guys.company_service.application.dto.request.CompanyUpdateRequestDto;
import com.chill_guys.company_service.application.dto.response.CompanyDetailResponseDto;
import com.chill_guys.company_service.application.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    // [배송 -> 회사]
    @GetMapping("/{companyId}/hub")
    public UUID getHubIdByCompanyId(@PathVariable UUID companyId) {
        return companyService. getHubIdByCompanyId(companyId);
    }

    // [생성]
    @PostMapping
    public ResponseEntity<CompanyDetailResponseDto> createCompany(
            @RequestBody CompanyCreateRequestDto requestDto,
            @RequestHeader(value = "X-User-Id") String userIdHeader) {
        return ResponseEntity.ok(companyService.createCompany(requestDto, userIdHeader));
    }

    // [개별 조회]
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDetailResponseDto> getCompany(@PathVariable UUID id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    // [전체 조회]
    @GetMapping
    public ResponseEntity<Page<CompanyDetailResponseDto>> getAllCompanies(Pageable pageable) {
        return ResponseEntity.ok(companyService.getAllCompanies(pageable));
    }

    // [수정]
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDetailResponseDto> updateCompany(
            @PathVariable UUID id,
            @RequestBody CompanyUpdateRequestDto updateDto,
            @RequestHeader(value = "X-User-Id") String userIdHeader) {
        return ResponseEntity.ok(companyService.updateCompany(id, updateDto, userIdHeader));
    }

    // [삭제]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable UUID id,  @RequestHeader(value = "X-User-Id") String userIdHeader) {
        companyService.deleteCompany(id, userIdHeader);
        return ResponseEntity.ok().build();
    }
}
