package com.kasia.model.Item;

import com.kasia.model.Model;
import com.kasia.model.group.Group;
import com.kasia.model.item.Item;
import com.kasia.model.unit.Unit;
import com.kasia.model.user.User;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ItemTest {
    private Item item;

    @Before
    public void before() {
        item = new Item();
    }

    // CONSTRUCTORS ================================================
    @Test
    public void constructorWithNoArgExist() {
        assertThat(new Item()).isNotNull();
    }

    @Test
    public void constructorWithAllArgExist() {
        int actualSumClassFields = item.getClass().getDeclaredFields().length;

        String name = "name";
        User maker = new User();
        Group group = new Group();
        String description = "description";
        Unit unit = new Unit();
        int expectedSumClassFields = 5;

        assertThat(actualSumClassFields).isEqualTo(expectedSumClassFields);
        assertThat(new Item(name, unit, description, group, maker)).isNotNull();
    }

    // IMPLEMENTS EXTENDS HASHCODE EQUALS TO_STRING ================================================
    @Test
    public void extendsModel() {
        assertThat(Model.class.isAssignableFrom(item.getClass())).isTrue();
    }

    @Test
    public void implementsSerializable() {
        assertThat(Serializable.class.isAssignableFrom(item.getClass())).isTrue();
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(item.getClass())
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void toStringIsOverride() {
        assertThat(item.toString().contains("{")).isTrue();
        assertThat(item.toString().contains(item.getClass().getSimpleName())).isTrue();
    }

    // GETTERS SETTERS ================================================
    @Test
    public void setAndGetName() {
        item.setName("name");
        assertThat(item.getName()).isEqualTo("name");
    }

    @Test
    public void setAndGetMaker() {
        User maker = new User();
        item.setMaker(maker);
        assertThat(item.getMaker()).isEqualTo(maker);
    }

    @Test
    public void setAndGetGroup() {
        Group group = new Group();
        item.setGroup(group);
        assertThat(item.getGroup()).isEqualTo(group);
    }

    @Test
    public void setAndGetDescription() {
        item.setDescription("text");
        assertThat(item.getDescription()).isEqualTo("text");
    }

}