package com.kasia.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DetailsTest {
    private Details details;

    @Before
    public void before() {
        details = new Details();
    }

    @Test
    public void setAndGetName() {
        details.setName("Name");
        assertThat(details.getName()).isEqualTo("Name");
    }

    @Test
    public void setAndGetSurname() {
        details.setSurname("Surname");
        assertThat(details.getSurname()).isEqualTo("Surname");
    }

    @Test
    public void setAndGetPosition() {
        details.setPosition("Position");
        assertThat(details.getPosition()).isEqualTo("Position");
    }

    @Test
    public void setAndGetNick() {
        details.setNick("Nick");
        assertThat(details.getNick()).isEqualTo("Nick");
    }

    @Test
    public void setAndGetEmail() {
        details.setEmail("Name@gmail.com");
        assertThat(details.getEmail()).isEqualTo("Name@gmail.com");
    }

    @Test
    public void setAndGetId() {
        details.setId(10L);
        assertThat(details.getId()).isEqualTo(10L);
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(Details.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void getDetailsFromAnotherDetails() {
        Details details2 = new Details();
        details2.setEmail("hej@gmail.com");
        details2.setNick("Tommy");
        details2.setPosition("Menedger");
        details2.setSurname("Surname");
        details2.setName("Name");
        details = new Details(details2);
        assertThat(details.getName()).isEqualTo(details2.getName());
        assertThat(details.getPosition()).isEqualTo(details2.getPosition());
        assertThat(details.getSurname()).isEqualTo(details2.getSurname());
        assertThat(details.getEmail()).isEqualTo(details2.getEmail());
        assertThat(details.getNick()).isEqualTo(details2.getNick());
    }

    @Test
    public void detailsImplementsSerializable() {
        Serializable serializable = new Details();
        assertThat(serializable).isNotNull();
    }

}