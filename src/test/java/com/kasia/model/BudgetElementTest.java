package com.kasia.model;

import com.kasia.ModelTestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class BudgetElementTest {
    @Test
    public void equalsAndHashCode() throws Exception {
        Element element1 = ModelTestData.element();
        Element element2 = ModelTestData.element();
        element2.setName("new" + element1.getName());

        Budget budget1 = ModelTestData.budget();
        Budget budget2 = ModelTestData.budget();
        budget1.setName("new" + budget1.getName());

        EqualsVerifier.forClass(BudgetElement.class)
                .usingGetClass()
                .withPrefabValues(Budget.class, budget1, budget2)
                .withPrefabValues(Element.class, element1, element2)
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}