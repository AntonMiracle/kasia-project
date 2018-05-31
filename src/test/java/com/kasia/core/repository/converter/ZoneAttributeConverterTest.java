package com.kasia.core.repository.converter;

import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ZoneAttributeConverterTest {
    private ZoneAttributeConverter converter;

    @Before
    public void before() {
        converter = new ZoneAttributeConverter();
    }

    @Test
    public void convertFromZoneIdToString() {
        ZoneId zone = ZoneId.systemDefault();
        assertThat(converter.convertToDatabaseColumn(zone)).isEqualTo(zone.toString());
    }

    @Test
    public void convertFromStringToZineId() {
        ZoneId zone = ZoneId.systemDefault();
        String id = zone.getId();
        assertThat(converter.convertToEntityAttribute(id)).isEqualTo(zone);
    }
}