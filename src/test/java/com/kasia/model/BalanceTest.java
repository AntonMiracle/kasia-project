package com.kasia.model;

import com.kasia.ModelTestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import java.util.Currency;

public class BalanceTest {
    @Test
    public void equals() throws Exception {
        EqualsVerifier.forClass(Balance.class)
                .usingGetClass()
                .withPrefabValues(Currency.class, ModelTestData.getBudgetCurrency1(), ModelTestData.getBudgetCurrency2())
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}