package com.kasia.model;

import com.kasia.ModelTestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationTest {
    @Test
    public void equalsAndHashCode() throws Exception {
        Element element1 = ModelTestData.element();
        Element element2 = ModelTestData.element();
        element2.setName("new" + element1.getName());

        Place place1 = ModelTestData.provider();
        Place place2 = ModelTestData.provider();
        place2.setName("new" + place1.getName());

        EqualsVerifier.forClass(Operation.class)
                .usingGetClass()
                .withPrefabValues(Element.class, element1, element2)
                .withPrefabValues(Place.class, place1, place2)
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

    @Test
    public void implementComparable() {
        Operation o1 = new Operation();
        Operation o2 = new Operation();
        Operation o3 = new Operation();
        Operation o4 = new Operation();
        Operation o5 = new Operation();
        Operation o6 = new Operation();

        LocalDateTime now = LocalDateTime.now();
        o1.setCreateOn(now);
        o2.setCreateOn(now.plusHours(1));
        o3.setCreateOn(now.plusHours(3));
        o4.setCreateOn(now.minusSeconds(50));
        o5.setCreateOn(now.minusDays(10));
        o6.setCreateOn(now.plusYears(10));

        o1.setId(3);
        o2.setId(4);
        o3.setId(5);
        o4.setId(2);
        o5.setId(1);
        o6.setId(6);

        Set<Operation> operations = new TreeSet<>();
        operations.add(o1);
        operations.add(o2);
        operations.add(o3);
        operations.add(o4);
        operations.add(o5);
        operations.add(o6);
        int id = 7;
        for (Operation o : operations) {
            assertThat(o.getId() == --id).isTrue();
        }
    }
}