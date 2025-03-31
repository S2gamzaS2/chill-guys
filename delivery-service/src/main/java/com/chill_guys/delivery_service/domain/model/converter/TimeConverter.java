package com.chill_guys.delivery_service.domain.model.converter;

import com.chill_guys.delivery_service.domain.model.Time;
import jakarta.persistence.AttributeConverter;

public class TimeConverter implements AttributeConverter<Time, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Time time) {
        return time == null ? null : time.getValue();
    }

    @Override
    public Time convertToEntityAttribute(Integer value) {
        return value == null ? null : new Time(value);
    }
}
