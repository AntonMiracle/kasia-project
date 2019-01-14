package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class UserConnectBudgetTest {
    @Test
    public void equals() throws Exception {
        EqualsVerifier.forClass(UserConnectBudget.class)
                .usingGetClass()
                .withPrefabValues(User.class, ModelTestData.getUser1(), ModelTestData.getUser2())
                .withPrefabValues(Budget.class, ModelTestData.getBudget1(), ModelTestData.getBudget2())
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

}