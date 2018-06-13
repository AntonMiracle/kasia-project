package com.kasia.model.price;

import com.kasia.model.Model;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PriceTest {
    private Price price;

    @Before
    public void before() {
        price = new Price();
    }

    // CONSTRUCTORS ================================================
    @Test
    public void constructorWithNoArgExist() {
        assertThat(new Price()).isNotNull();
    }

    @Test
    public void constructorWithAllArgExist() {
        int actualSumClassFields = price.getClass().getDeclaredFields().length;

        long amount = 100L;
        int expectedSumClassFields = 1;

        assertThat(actualSumClassFields).isEqualTo(expectedSumClassFields);
        assertThat(new Price(amount)).isNotNull();
    }
    // ================================================

    @Test
    public void setAndGetAmount() {
        price.setAmount(10L);
        assertThat(price.getAmount()).isEqualTo(10L);
    }

    @Test
    public void notExtendsModel() {
        assertThat(Model.class.isAssignableFrom(price.getClass())).isFalse();
    }

    @Test
    public void implementsSerializable() {
        assertThat(Serializable.class.isAssignableFrom(price.getClass())).isTrue();
    }


    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(price.getClass())
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void toStringIsOverride() {
        assertThat(price.toString().contains("{")).isTrue();
        assertThat(price.toString().contains(price.getClass().getSimpleName())).isTrue();
    }
}