package com.kasia.model.group;

import com.kasia.model.Model;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GroupTest {
    private Group group;

    @Before
    public void before() {
        group = new Group();
    }

    // CONSTRUCTORS ================================================
    @Test
    public void constructorWithNoArgExist() {
        assertThat(new Group()).isNotNull();
    }

    @Test
    public void constructorWithAllArgExist() {
        int actualSumClassFields = group.getClass().getDeclaredFields().length;

        String name = "name";
        Type type = Type.SYSTEM;
        int expectedSumClassFields = 2;

        assertThat(actualSumClassFields).isEqualTo(expectedSumClassFields);
        assertThat(new Group(name, type)).isNotNull();
    }

    // IMPLEMENTS EXTENDS HASHCODE EQUALS TO_STRING ================================================
    @Test
    public void extendsModel() {
        assertThat(Model.class.isAssignableFrom(group.getClass())).isTrue();
    }

    @Test
    public void implementsSerializable() {
        assertThat(Serializable.class.isAssignableFrom(group.getClass())).isTrue();
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(group.getClass())
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void toStringIsOverride() {
        assertThat(group.toString().contains("{")).isTrue();
        assertThat(group.toString().contains(group.getClass().getSimpleName())).isTrue();
    }

    // GETTERS SETTERS ================================================
    @Test
    public void setAndGetName() {
        group.setName("name");
        assertThat(group.getName()).isEqualTo("name");
    }

    @Test
    public void setAndGetType() {
        group.setType(Type.ARTICLE);
        assertThat(group.getType()).isEqualTo(Type.ARTICLE);
    }
}