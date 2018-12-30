package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class BudgetTest {
    @Test
    public void equalsAndHashCode() throws Exception {

        EqualsVerifier.forClass(Budget.class)
                .usingGetClass()
                .withPrefabValues(Price.class, ModelTestHelper.getPrice1(), ModelTestHelper.getPrice2())
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}