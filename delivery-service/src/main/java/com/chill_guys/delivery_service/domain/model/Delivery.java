package com.chill_guys.delivery_service.domain.model;

import com.chill_guys.delivery_service.application.dto.request.DeliveryUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;


import java.util.UUID;

@Entity
@Getter
@Table(name = "p_delivery")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseTimeEntity{

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus = DeliveryStatus.PENDING;

    @Column(nullable = false)
    private UUID departureHubId;

    @Column(nullable = false)
    private UUID destinationHubId;

    @Column(nullable = false)
    private UUID productId;

    @Embedded
    private RecipientCompany recipientCompany;

    @Column
    private Long deliveryManagerId;


    @Builder
    private Delivery(UUID orderId,
                    UUID departureHubId,
                    UUID destinationHubId,
                    UUID productId,
                    RecipientCompany recipientCompany,
                     Long deliveryManagerId) {
        this.orderId = orderId;
        this.departureHubId = departureHubId;
        this.destinationHubId = destinationHubId;
        this.productId = productId;
        this.recipientCompany = recipientCompany;
        this.deliveryManagerId = deliveryManagerId;
    }

    public void updateOf(DeliveryUpdateRequestDto dto) {
        RecipientCompany recipientCompany = RecipientCompany.builder()
                .companyId(dto.getCompanyId())
                .address(dto.getAddress())
                .slackId(dto.getSlackId())
                .phone(dto.getPhoneNumber())
                .build();
        this.departureHubId = dto.getDepartureHubId();
        this.destinationHubId = dto.getDestinationHubId();
        this.productId = dto.getProductId();
        this.recipientCompany = recipientCompany;
        this.deliveryManagerId = dto.getDeliveryManagerId();
    }

    public void assignDeliveryManager(Long deliveryManagerId) {
        this.deliveryManagerId = deliveryManagerId;
        this.deliveryStatus = DeliveryStatus.SHIPPING;
    }

    public void changeDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public boolean isInfoChangeable() {
        return this.deliveryStatus == DeliveryStatus.PENDING;
    }

}
