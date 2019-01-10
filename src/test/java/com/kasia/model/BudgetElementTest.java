package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class BudgetElementTest {
    @Test
    public void equalsAndHashCode() throws Exception {

        EqualsVerifier.forClass(BudgetElement.class)
                .usingGetClass()
                .withPrefabValues(Budget.class, ModelTestHelper.getBudget1(), ModelTestHelper.getBudget2())
                .withPrefabValues(Element.class, ModelTestHelper.getElement1(), ModelTestHelper.getElement2())
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}