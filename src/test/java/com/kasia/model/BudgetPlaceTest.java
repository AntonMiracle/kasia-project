package com.kasia.model;

import com.kasia.ModelTestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class BudgetPlaceTest {
    @Test
    public void equals() throws Exception {
        Budget budget1 = ModelTestData.budget();
        Budget budget2 = ModelTestData.budget();
        budget2.setName("new" + budget1.getName());
        Place place1 = ModelTestData.provider();
        Place place2 = ModelTestData.provider();
        place2.setName("new" + place1.getName());

        EqualsVerifier.forClass(BudgetPlace.class)
                .usingGetClass()
                .withPrefabValues(Budget.class, budget1, budget2)
                .withPrefabValues(Place.class, place1, place2)
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }
}