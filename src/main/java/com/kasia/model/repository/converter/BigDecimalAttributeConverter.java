package com.kasia.model.repository.converter;

import javax.persistence.AttributeConverter;
import java.math.BigDecimal;

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
