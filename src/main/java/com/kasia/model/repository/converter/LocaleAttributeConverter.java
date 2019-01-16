package com.kasia.model.repository.converter;

import javax.persistence.AttributeConverter;
import java.util.Locale;

public class LocaleAttributeConverter implements AttributeConverter<Locale, String> {
    private final String SEPARATOR = ",";
    @Override
    public String convertToDatabaseColumn(Locale locale) {
        return locale.getLanguage() + SEPARATOR + locale.getCountry();
    }

    @Override
    public Locale convertToEntityAttribute(String s) {
        String l = s.split(SEPARATOR)[0];
        String c = s.split(SEPARATOR)[1];
        return new Locale(l,c);
    }
}
