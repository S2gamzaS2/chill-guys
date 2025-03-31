package com.chill_guys.delivery_service.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Time {

    private Integer minutes;

    public Time(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getValue() {
        return minutes;
    }

    @Override
    public String toString() {
        int hour = this.minutes / 60;
        int minute = this.minutes % 60;
        return String.format("%02d:%02d", hour, minute);
    }
}
