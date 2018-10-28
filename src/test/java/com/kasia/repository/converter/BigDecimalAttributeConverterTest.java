package com.kasia.repository.converter;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BigDecimalAttributeConverterTest {
    private BigDecimalAttributeConverter converter = new BigDecimalAttributeConverter();
    @Test
    public void convertToDatabaseColumn() throws Exception {
        String expected = "10.01";
        assertThat(converter.convertToDatabaseColumn(BigDecimal.valueOf(10.01))).isEqualTo(expected);
    }

    @Test
    public void convertToEntityAttribute() throws Exception {
        BigDecimal expected = BigDecimal.valueOf(10.01);
        assertThat(converter.convertToEntityAttribute("10.01")).isEqualTo(expected);
    }
}