package com.kasia.core.repository.converter;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocaleAttributeConverterTest {
    private LocaleAttributeConverter converter;

    @Before
    public void before() {
        converter = new LocaleAttributeConverter();
    }

    @Test
    public void convertFromLocaleToString() {
        Locale locale = Locale.getDefault();
        String lang = locale.getLanguage();
        String country = locale.getCountry();
        assertThat(converter.convertToDatabaseColumn(locale)).isEqualTo(lang + converter.SEPARATOR + country);
    }

    @Test
    public void convertFromStringToLocal() {
        Locale locale = Locale.getDefault();
        String lang = locale.getLanguage();
        String country = locale.getCountry();
        assertThat(converter.convertToEntityAttribute(lang + converter.SEPARATOR + country)).isEqualTo(locale);
    }
}