package com.kasia.model.repository.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Converter
public class BigDecimalAttributeConverter implements AttributeConverter<BigDecimal, String> {
    @Override
    public String convertToDatabaseColumn(BigDecimal bigDecimal) {
        return bigDecimal.toString();
    }

    @Override
    public BigDecimal convertToEntityAttribute(String s) {
        return new BigDecimal(s);
    }
}
