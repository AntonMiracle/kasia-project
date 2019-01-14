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
                .withPrefabValues(Budget.class, ModelTestData.getBudget1(), ModelTestData.getBudget2())
                .withPrefabValues(User.class, ModelTestData.getUser1(), ModelTestData.getUser2())
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}