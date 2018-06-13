package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ModelTest {
    // CONSTRUCTORS ================================================
    @Test
    public void constructorWithNoArgExist() {
        assertThat(new Model()).isNotNull();
    }

    @Test
    public void constructorWithAllArgExist() {
        int actualSumClassFields = new Model().getClass().getDeclaredFields().length;
        int expectedSumClassFields = 1;

        long id = 10L;

        assertThat(actualSumClassFields).isEqualTo(expectedSumClassFields);
        assertThat(new Model(id)).isNotNull();
    }
    // ================================================

    @Test
    public void setAndGetId() {
        Model model = new Model();
        model.setId(10L);
        assertThat(model.getId()).isEqualTo(10L);
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(Model.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void toStringIsOverride() {
        assertThat(new Model().toString().contains("{")).isTrue();
        assertThat(new Model().toString().contains(new Model().getClass().getSimpleName())).isTrue();
    }
}