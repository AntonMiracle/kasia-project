package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class UserBudgetTest {
    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(UserBudget.class)
                .usingGetClass()
                .withPrefabValues(Budget.class, ModelTestHelper.getBudget1(), ModelTestHelper.getBudget2())
                .withPrefabValues(User.class, ModelTestHelper.getUser1(), ModelTestHelper.getUser2())
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}