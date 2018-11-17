package com.kasia.model.repository.converter;

import org.junit.Test;

import java.time.ZoneId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ZoneIdAttributeConverterTest {
    private ZoneIdAttributeConverter convarter = new ZoneIdAttributeConverter();

    @Test
    public void convertToDatabaseColumn() throws Exception {
        ZoneId zoneId = ZoneId.of("Europe/Belgrade");
        String expected = "Europe/Belgrade";
        assertThat(convarter.convertToDatabaseColumn(zoneId)).isEqualTo(expected);
    }

    @Test
    public void convertToEntityAttribute() throws Exception {
        ZoneId expected = ZoneId.of("Europe/Belgrade");
        String zoneId = "Europe/Belgrade";
        assertThat(convarter.convertToEntityAttribute(zoneId)).isEqualTo(expected);
    }
}