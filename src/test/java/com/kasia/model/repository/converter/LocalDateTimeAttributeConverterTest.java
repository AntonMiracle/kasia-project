package com.kasia.model.repository.converter;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocalDateTimeAttributeConverterTest {
    LocalDateTimeAttributeConverter converter = new LocalDateTimeAttributeConverter();

    @Test
    public void convertToDatabaseColumn() throws Exception {
        LocalDateTime now = LocalDateTime.of(2018, 10, 28, 20, 0, 0);
        String expected = "2018-10-28 20:00:00";
        assertThat(converter.convertToDatabaseColumn(now)).isEqualTo(expected);
    }

    @Test
    public void convertToEntityAttribute() throws Exception {
        LocalDateTime expected = LocalDateTime.of(2018, 10, 28, 20, 0, 0);
        String now = "2018-10-28 20:00:00";
        assertThat(converter.convertToEntityAttribute(now)).isEqualTo(expected);
    }
}