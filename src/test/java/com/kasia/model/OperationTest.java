package com.kasia.model;

import com.kasia.ModelTestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class OperationTest {
    @Test
    public void equalsAndHashCode() throws Exception {
        EqualsVerifier.forClass(Operation.class)
                .usingGetClass()
                .withPrefabValues(Element.class, ModelTestData.getElement1(), ModelTestData.getElement2())
                .withPrefabValues(Provider.class, ModelTestData.getElementProvider1(), ModelTestData.getElementProvider2())
                .withPrefabValues(Price.class, ModelTestData.getPrice1(), ModelTestData.getPrice2())
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

}