package com.kasia.model;

import com.kasia.ModelTestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.persistence.Id;

public class ElementTest {
    @Test
    public void equalsAndHashCode() throws Exception {
        EqualsVerifier.forClass(Element.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredAnnotations(Id.class)
                .verify();
    }

}