package com.kasia.model;

import com.kasia.ModelTestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class BudgetProviderTest {
    @Test
    public void equals() throws Exception {

        EqualsVerifier.forClass(BudgetProvider.class)
                .usingGetClass()
                .withPrefabValues(Budget.class, ModelTestData.getBudget1(), ModelTestData.getBudget2())
                .withPrefabValues(Provider.class, ModelTestData.getElementProvider1(), ModelTestData.getElementProvider2())
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

}