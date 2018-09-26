package com.kasia.repository.converter;

import javax.persistence.AttributeConverter;
import java.time.LocalDateTime;

public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, String> {

    @Override
    public String convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String s) {
        return LocalDateTime.parse(s);
    }
}
