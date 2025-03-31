package com.chill_guys.delivery_service.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Distance {

    private Double distance;

    public Distance(Double distance) {
        this.distance = distance;
    }

    public Double getValue() {
        return distance;
    }

    @Override
    public String toString() {
        return String.format("%.2f", distance);
    }
}
