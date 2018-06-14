package com.kasia.model.Item;

import com.kasia.model.Model;
import com.kasia.model.group.Group;
import com.kasia.model.group.Type;
import com.kasia.model.item.Item;
import com.kasia.model.price.Price;
import com.kasia.model.unit.Unit;
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
        Group group = new Group();
        String description = "description";
        Unit unit = new Unit();
        Price price = new Price();
        int expectedSumClassFields = 5;

        assertThat(actualSumClassFields).isEqualTo(expectedSumClassFields);
        assertThat(new Item(name, unit, price, description, group)).isNotNull();
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
                .withPrefabValues(Group.class, new Group(), new Group("name", Type.ITEM))
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

    @Test
    public void setAndGetPrice() {
        Price price = new Price();
        item.setPrice(price);
        assertThat(item.getPrice()).isEqualTo(price);
    }

    @Test
    public void setAndGetUnit() {
        Unit unit = new Unit();
        item.setUnit(unit);
        assertThat(item.getUnit()).isEqualTo(unit);
    }

}