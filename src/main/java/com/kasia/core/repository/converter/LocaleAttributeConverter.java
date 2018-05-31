package com.kasia.core.repository.converter;

import javax.persistence.AttributeConverter;
import java.util.Locale;

public class LocaleAttributeConverter implements AttributeConverter<Locale, String> {
    public final String SEPARATOR = ":";

    @Override
    public String convertToDatabaseColumn(Locale attribute) {
        String lang = attribute.getLanguage();
        String country = attribute.getCountry();
        return lang + SEPARATOR + country;
    }

    @Override
    public Locale convertToEntityAttribute(String dbData) {
        String[] langCountry = dbData.split(SEPARATOR);
        return new Locale(langCountry[0], langCountry[1]);
    }
}
