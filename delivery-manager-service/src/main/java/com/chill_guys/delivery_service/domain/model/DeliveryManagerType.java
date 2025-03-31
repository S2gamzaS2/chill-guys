package com.chill_guys.delivery_service.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum DeliveryManagerType {
    HUB,
    COMPANY;

    public static DeliveryManagerType of(String type) {
        return valueOf(type);
    }

    @JsonCreator
    public static DeliveryManagerType parsing(String inputValue) {
        return Stream.of(DeliveryManagerType.values())
                .filter(deliveryStatus -> deliveryStatus.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
