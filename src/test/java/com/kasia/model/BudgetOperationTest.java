package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class BudgetOperationTest {
    @Test
    public void equalsAndHashCode() throws Exception {

        EqualsVerifier.forClass(BudgetOperation.class)
                .usingGetClass()
                .withPrefabValues(Budget.class, ModelTestData.getBudget1(), ModelTestData.getBudget2())
                .withPrefabValues(Operation.class, ModelTestData.getOperation1(), ModelTestData.getOperation2())
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

}