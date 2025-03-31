package com.chill_guys.delivery_service.domain.model.converter;

import com.chill_guys.delivery_service.domain.model.Distance;
import jakarta.persistence.AttributeConverter;

public class DistanceConverter implements AttributeConverter<Distance, Double> {

    @Override
    public Double convertToDatabaseColumn(Distance distance) {
        return distance == null ? null : distance.getValue();
    }

    @Override
    public Distance convertToEntityAttribute(Double value) {
        return value == null ? null : new Distance(value);
    }
}
