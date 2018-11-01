package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class EmployerTest {

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(Employer.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}