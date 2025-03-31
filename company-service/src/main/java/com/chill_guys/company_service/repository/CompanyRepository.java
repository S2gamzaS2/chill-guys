package com.chill_guys.company_service.repository;

import com.chill_guys.company_service.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByIdAndDeletedAtIsNull(UUID id);
    Page<Company> findAllByDeletedAtIsNull(Pageable pageable);
}