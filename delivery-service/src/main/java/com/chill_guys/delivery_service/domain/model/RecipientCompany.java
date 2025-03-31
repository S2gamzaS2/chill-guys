package com.chill_guys.delivery_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class RecipientCompany {

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "company_slack_id")
    private String slackId;

    @Column(name = "company_phone")
    private String phone;

    @Builder
    private RecipientCompany(UUID companyId, String address, String slackId, String phone) {
        this.companyId = companyId;
        this.address = address;
        this.slackId = slackId;
        this.phone = phone;
    }
}
