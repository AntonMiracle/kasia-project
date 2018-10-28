package com.kasia.repository.converter;

import javax.persistence.AttributeConverter;
import java.time.ZoneId;

public class ZoneIdAttributeConverter implements AttributeConverter<ZoneId, String> {

    @Override
    public String convertToDatabaseColumn(ZoneId zoneId) {
        return zoneId.toString();
    }

    @Override
    public ZoneId convertToEntityAttribute(String s) {
        return ZoneId.of(s);
    }
}
