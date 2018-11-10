package com.kasia.repository.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.ZoneId;

@Converter
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
