package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class BudgetOperationTest {
    @Test
    public void equalsAndHashCode() throws Exception {

        EqualsVerifier.forClass(BudgetOperation.class)
                .usingGetClass()
                .withPrefabValues(Budget.class,ModelTestHelper.getBudget1(), ModelTestHelper.getBudget2())
                .withPrefabValues(Operation.class,ModelTestHelper.getOperation1(), ModelTestHelper.getOperation2())
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}