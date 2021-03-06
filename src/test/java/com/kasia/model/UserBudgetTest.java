package com.kasia.model;

import com.kasia.ModelTestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class UserBudgetTest {
    @Test
    public void equalsAndHashCode() {
        Budget budget1 = ModelTestData.budget();
        Budget budget2 = ModelTestData.budget();
        budget1.setName("new" + budget1.getName());

        User user1 = ModelTestData.user();
        User user2 = ModelTestData.user();
        user2.setEmail("new" + user1.getEmail());

        EqualsVerifier.forClass(UserBudget.class)
                .usingGetClass()
                .withPrefabValues(Budget.class, budget1, budget2)
                .withPrefabValues(User.class, user1, user2)
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}