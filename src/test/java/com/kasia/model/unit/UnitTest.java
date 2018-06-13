package com.kasia.model.unit;

import com.kasia.model.Model;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UnitTest {
    private Unit unit;

    @Before
    public void before() {
        unit = new Unit();
    }

    // CONSTRUCTORS ================================================
    @Test
    public void constructorWithNoArgExist() {
        assertThat(new Unit()).isNotNull();
    }

    @Test
    public void constructorWithAllArgExist() {
        int actualSumClassFields = unit.getClass().getDeclaredFields().length;

        String name = "kg";
        int amount = 1;
        int expectedSumClassFields = 2;

        assertThat(actualSumClassFields).isEqualTo(expectedSumClassFields);
        assertThat(new Unit(name, amount)).isNotNull();
    }

    // IMPLEMENTS EXTENDS HASHCODE EQUALS TO_STRING ================================================
    @Test
    public void extendsModel() {
        assertThat(Model.class.isAssignableFrom(unit.getClass())).isTrue();
    }

    @Test
    public void implementsSerializable() {
        assertThat(Serializable.class.isAssignableFrom(unit.getClass())).isTrue();
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(unit.getClass())
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void toStringIsOverride() {
        assertThat(unit.toString().contains("{")).isTrue();
        assertThat(unit.toString().contains(unit.getClass().getSimpleName())).isTrue();
    }

    // GETTERS SETTERS ================================================
    @Test
    public void setAndGetName() {
        unit.setName("name");
        assertThat(unit.getName()).isEqualTo("name");
    }

    @Test
    public void setAndGetAmount() {
        unit.setAmount(1);
        assertThat(unit.getAmount()).isEqualTo(1);
    }
}