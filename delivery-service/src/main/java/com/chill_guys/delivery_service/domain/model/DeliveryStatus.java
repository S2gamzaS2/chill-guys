package com.chill_guys.delivery_service.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum DeliveryStatus {
    PENDING,
    SHIPPING,
    DELIVERED;

    public static DeliveryStatus of(String status) {
        return valueOf(status);
    }

    @JsonCreator
    public static DeliveryStatus parsing(String inputValue) {
        return Stream.of(DeliveryStatus.values())
                .filter(deliveryStatus -> deliveryStatus.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
