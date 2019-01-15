package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class PriceTest {
    @Test
    public void equals() throws Exception {
        EqualsVerifier.forClass(Price.class)
                .usingGetClass()
                .withPrefabValues(Currencies.class, Currencies.EUR, Currencies.PLN)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}