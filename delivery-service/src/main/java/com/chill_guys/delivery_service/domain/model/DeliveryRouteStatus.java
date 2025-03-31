package com.chill_guys.delivery_service.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum DeliveryRouteStatus {
    PENDING,
    TRANSIT,
    ARRIVED,
    DELIVERING;

    public static DeliveryRouteStatus of(String status) {
        return valueOf(status);
    }

    @JsonCreator
    public static DeliveryRouteStatus parsing(String inputValue) {
        return Stream.of(DeliveryRouteStatus.values())
                .filter(deliveryStatus -> deliveryStatus.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
