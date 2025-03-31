package com.chill_guys.delivery_service.domain.model;

import com.chill_guys.delivery_service.application.dto.request.DeliveryManagerUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_delivery_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryManager extends BaseTimeEntity{

    @Id
    private Long id;

    @Column
    private UUID hubId;

    @Column(nullable = false)
    private String slackId;

    @Column
    private DeliveryManagerType type;

    @Column(nullable = false)
    private Long sequence;

    @Builder
    private DeliveryManager(Long id, UUID hubId, String slackId, DeliveryManagerType type, Long sequence) {
        this.id = id;
        this.hubId = hubId;
        this.slackId = slackId;
        this.type = type;
        this.sequence = sequence;
    }

    public void updateOf(DeliveryManagerUpdateRequestDto deliveryManagerUpdateRequestDto) {
        this.hubId = deliveryManagerUpdateRequestDto.getHubId();
        this.slackId = deliveryManagerUpdateRequestDto.getSlackId();
        this.type = deliveryManagerUpdateRequestDto.getType();
        this.sequence = deliveryManagerUpdateRequestDto.getSequence();
    }

    public void changeHubId(UUID endHubId, Long maxSequence) {
        this.hubId = endHubId;
        this.sequence = maxSequence + 1;
    }

    public void setHubAndType(UUID hubId, DeliveryManagerType type) {
        this.hubId = hubId;
        this.type = type;
    }

    public void updateSlackId(String slackId) {
        this.slackId = slackId;
    }
}
