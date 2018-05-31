package com.kasia.core.repository.converter;

import javax.persistence.AttributeConverter;
import java.util.Locale;

public class LocaleAttributeConverter implements AttributeConverter<Locale, String> {
    @Override
    public String convertToDatabaseColumn(Locale attribute) {
        return attribute.toString();
    }

    @Override
    public Locale convertToEntityAttribute(String dbData) {
        return new Locale(dbData);
    }
}
