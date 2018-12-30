package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class OperationTest {
    @Test
    public void equalsAndHashCode() throws Exception {
        EqualsVerifier.forClass(Operation.class)
                .usingGetClass()
                .withPrefabValues(Element.class, ModelTestHelper.getElement1(), ModelTestHelper.getElement2())
                .withPrefabValues(ElementProvider.class, ModelTestHelper.getElementProvider1(), ModelTestHelper.getElementProvider2())
                .withPrefabValues(Price.class, ModelTestHelper.getPrice1(), ModelTestHelper.getPrice2())
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}