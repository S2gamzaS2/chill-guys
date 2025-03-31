package com.chill_guys.company_service.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class HubId {
    private UUID hubId;
}
