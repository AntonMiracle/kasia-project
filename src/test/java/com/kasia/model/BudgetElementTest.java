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
                .withPrefabValues(Budget.class, ModelTestData.getBudget1(), ModelTestData.getBudget2())
                .withPrefabValues(Element.class, ModelTestData.getElement1(), ModelTestData.getElement2())
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}