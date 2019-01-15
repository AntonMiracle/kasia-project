package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class BalanceTest {
    @Test
    public void equals() throws Exception {
        EqualsVerifier.forClass(Balance.class)
                .usingGetClass()
                .withPrefabValues(Currencies.class, Currencies.EUR, Currencies.RUB)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}