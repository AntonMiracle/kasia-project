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

    @Test
    public void priceRegexTest() {
        String regex = "^(0*[1-9]\\d*)|(0*\\d*[.,][0-9]+)$";
        System.out.println(regex);
        String s1 = "000001";
        String s2 = "0";
        String s3 = "01";
        String s4 = "1";
        String s5 = "1000";
        String s6 = "1000";
        String s7 = "1111";
        String s8 = "101";
        String s9 = "0000";

        String d1 = "0.000001";
        String d2 = "0.";
        String d3 = "0.1";
        String d4 = "1.0";
        String d5 = "1000.0";
        String d6 = "1000.0";
        String d7 = "1111.1";
        String d8 = "101.0";
        String d9 = "0.000";
        String d10 = "00000000.0001";
        String d11 = "00000000.1000";

        assertThat(s1.matches(regex)).isTrue();
        assertThat(s2.matches(regex)).isFalse();
        assertThat(s3.matches(regex)).isTrue();
        assertThat(s4.matches(regex)).isTrue();
        assertThat(s5.matches(regex)).isTrue();
        assertThat(s6.matches(regex)).isTrue();
        assertThat(s7.matches(regex)).isTrue();
        assertThat(s8.matches(regex)).isTrue();
        assertThat(s9.matches(regex)).isFalse();

        assertThat(d1.matches(regex)).isTrue();
        assertThat(d2.matches(regex)).isFalse();
        assertThat(d3.matches(regex)).isTrue();
        assertThat(d4.matches(regex)).isTrue();
        assertThat(d5.matches(regex)).isTrue();
        assertThat(d6.matches(regex)).isTrue();
        assertThat(d7.matches(regex)).isTrue();
        assertThat(d8.matches(regex)).isTrue();
        assertThat(d9.matches(regex)).isTrue();
        assertThat(d10.matches(regex)).isTrue();
        assertThat(d11.matches(regex)).isTrue();

    }
}