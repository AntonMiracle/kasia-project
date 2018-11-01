package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public class OperationTest {

    @Test
    public void checkEqualsAndHashCode() {
        Operation op1 = new Operation(BigDecimal.TEN, new Article(), new User(), new Employer(), LocalDateTime.now());
        Operation op2 = new Operation(BigDecimal.ZERO, new Article(), new User(), new Employer(), LocalDateTime.now());
        Budget b1 = new Budget("Name1",BigDecimal.TEN, Currency.getInstance("EUR"),LocalDateTime.now());
        Budget b2 = new Budget("Name2",BigDecimal.TEN, Currency.getInstance("USD"),LocalDateTime.now());

        EqualsVerifier.forClass(Operation.class)
                .usingGetClass()
                .withPrefabValues(Operation.class, op1, op2)
                .withPrefabValues(Budget.class, b1, b2)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}