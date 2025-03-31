package com.chill_guys.company_service.domain;

import com.chill_guys.company_service.application.dto.request.CompanyCreateRequestDto;
import com.chill_guys.company_service.application.dto.request.CompanyUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "p_company")
public class Company {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Embedded
    private HubId hubId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CompanyType type;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "deleted_by")
    private Long deletedBy;

    public static Company create(CompanyCreateRequestDto requestDto, HubId hubId, Long userId) {
        return Company.builder()
                .id(UUID.randomUUID())
                .hubId(hubId)
                .name(requestDto.getName())
                .type(requestDto.getType())
                .address(requestDto.getAddress())
                .phone(requestDto.getPhone())
                .createdBy(userId)
                .createdAt(Timestamp.from(Instant.now()))
                .updatedBy(userId)
                .updatedAt(Timestamp.from(Instant.now()))
                .deletedBy(null)
                .deletedAt(null)
                .build();
    }

    public void updateCompany(CompanyUpdateRequestDto dto, Long updatedBy) {
        this.name = dto.getName();
        this.type = dto.getType();
        this.address = dto.getAddress();
        this.phone = dto.getPhone();
        this.updatedBy = updatedBy;
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public void deleteCompany(Long deletedBy) {
        this.deletedAt = Timestamp.from(Instant.now());
        this.deletedBy = deletedBy;
    }
}

