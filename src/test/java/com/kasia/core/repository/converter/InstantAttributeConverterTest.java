package com.kasia.core.repository.converter;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InstantAttributeConverterTest {
    private InstantAttributeConverter converter;

    @Before
    public void before() {
        converter = new InstantAttributeConverter();
    }

    @Test
    public void convertFromInstantToTimestamp() {
        Instant now = Instant.now();
        assertThat(converter.convertToDatabaseColumn(now)).isEqualTo(Timestamp.from(now));
    }

    @Test
    public void convertFromTimestampToInstant() {
        Timestamp now = Timestamp.from(Instant.now());
        assertThat(converter.convertToEntityAttribute(now)).isEqualTo(now.toInstant());
    }
}