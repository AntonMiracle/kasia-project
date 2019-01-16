package com.kasia.model.repository.converter;

import javax.persistence.AttributeConverter;
import java.util.Locale;
import java.util.StringTokenizer;

public class LocaleAttributeConverter implements AttributeConverter<Locale, String> {
    private final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Locale locale) {
        return locale.getLanguage() + SEPARATOR + locale.getCountry();
    }

    @Override
    public Locale convertToEntityAttribute(String s) {
        StringTokenizer st = new StringTokenizer(s, SEPARATOR);
        return new Locale(st.nextToken(), st.nextToken());
    }
}
