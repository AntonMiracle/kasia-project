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
                .withPrefabValues(User.class, ModelTestHelper.getUser1(), ModelTestHelper.getUser2())
                .withPrefabValues(Budget.class, ModelTestHelper.getBudget1(), ModelTestHelper.getBudget2())
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

}