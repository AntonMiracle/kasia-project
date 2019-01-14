package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class BudgetElementProviderTest {
    @Test
    public void equals() throws Exception {

        EqualsVerifier.forClass(BudgetElementProvider.class)
                .usingGetClass()
                .withPrefabValues(Budget.class, ModelTestData.getBudget1(), ModelTestData.getBudget2())
                .withPrefabValues(ElementProvider.class, ModelTestData.getElementProvider1(), ModelTestData.getElementProvider2())
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

}