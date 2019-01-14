package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class ElementTest {
    @Test
    public void equalsAndHashCode() throws Exception {
        EqualsVerifier.forClass(Element.class)
                .usingGetClass()
                .withPrefabValues(Price.class, ModelTestData.getPrice1(), ModelTestData.getPrice2())
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

}