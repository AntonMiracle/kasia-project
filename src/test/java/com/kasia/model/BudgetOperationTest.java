package com.kasia.model;

import com.kasia.ModelTestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class BudgetOperationTest {
    @Test
    public void equalsAndHashCode() throws Exception {
        Budget budget1 = ModelTestData.budget();
        Budget budget2 = ModelTestData.budget();
        budget1.setName("new" + budget1.getName());

        Operation operation1 = ModelTestData.operation();
        Operation operation2 = ModelTestData.operation();
        operation1.setId(1);
        operation2.setId(2);

        EqualsVerifier.forClass(BudgetOperation.class)
                .usingGetClass()
                .withPrefabValues(Budget.class, budget1, budget2)
                .withPrefabValues(Operation.class, operation1, operation2)
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

}