package com.kasia.core.repository.converter;

import javax.persistence.AttributeConverter;
import java.time.ZoneId;

public class ZoneAttributeConverter implements AttributeConverter<ZoneId, String> {
    @Override
    public String convertToDatabaseColumn(ZoneId attribute) {
        return attribute.getId();
    }

    @Override
    public ZoneId convertToEntityAttribute(String dbData) {
        return ZoneId.of(dbData);
    }
}
