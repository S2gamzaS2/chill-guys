package com.chill_guys.delivery_service.domain.model;

import com.chill_guys.delivery_service.application.dto.request.DeliveryRouteUpdateRequestDto;
import com.chill_guys.delivery_service.domain.model.converter.DistanceConverter;
import com.chill_guys.delivery_service.domain.model.converter.TimeConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_delivery_route")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryRoute extends BaseTimeEntity{

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private UUID deliveryId;

    @Column(nullable = false)
    private Integer sequence;

    @Column(nullable = false)
    private UUID startHudId;

    @Column(nullable = false)
    private UUID endHudId;

    @Convert(converter = DistanceConverter.class)
    @Column(name = "estimated_distance", nullable = false)
    private Distance estimatedDistance;

    @Convert(converter = DistanceConverter.class)
    @Column(name = "actual_distance", nullable = false)
    private Distance actualDistance;

    @Convert(converter = TimeConverter.class)
    @Column(name = "estimated_time", nullable = false)
    private Time estimatedTime;

    @Convert(converter = TimeConverter.class)
    @Column(name = "actual_time", nullable = false)
    private Time actualTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'PENDING'")
    private DeliveryRouteStatus status = DeliveryRouteStatus.PENDING;

    @Column
    private Long hubDeliveryManagerId;

    @Builder
    private DeliveryRoute(UUID deliveryId, Integer sequence, UUID startHudId, UUID endHudId,
                          Distance estimatedDistance, Distance actualDistance, Time estimatedTime, Time actualTime) {
        this.deliveryId = deliveryId;
        this.sequence = sequence;
        this.startHudId = startHudId;
        this.endHudId = endHudId;
        this.estimatedDistance = estimatedDistance;
        this.actualDistance = actualDistance;
        this.estimatedTime = estimatedTime;
        this.actualTime = actualTime;
    }

    public void updateOf(DeliveryRouteUpdateRequestDto dto) {
        this.startHudId = dto.getStartHubId();
        this.endHudId = dto.getEndHubId();
        this.estimatedDistance = dto.getEstimatedDistance();
        this.actualDistance = dto.getActualDistance();
        this.estimatedTime = dto.getEstimatedTime();
        this.actualTime = dto.getActualTime();
        this.hubDeliveryManagerId = dto.getHubDeliveryManagerId();
    }

    public void assignHubDeliveryManager(Long hudDeliveryManagerId) {
        this.hubDeliveryManagerId = hudDeliveryManagerId;
        this.status = DeliveryRouteStatus.TRANSIT;
    }

    public void changeDeliveryStatus(DeliveryRouteStatus status) {
        this.status = status;
    }

    public boolean isInfoChangeable() {
        return this.status == DeliveryRouteStatus.PENDING;
    }
}
