package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class ElementProviderTest {
    @Test
    public void equalsAndHashCode() throws Exception {
        EqualsVerifier.forClass(ElementProvider.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}