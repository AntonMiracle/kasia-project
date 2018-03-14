package com.kasia.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RoleTest {
    private Role role;

    @Before
    public void before() {
        role = new Role("n-322");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameWithNullThenIAE() {
        role.setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameArgumentSmallerThenMinNameLengthThenIAE() {
        role.setName("nam");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameArgumentBiggerThenMaxNameLengthThenIAE() {
        role.setName("namenamenamenamee");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameArgumentHasDashInTheEndThenIAE() {
        role.setName("name-");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameArgumentHasDashFromStartThenIAE() {
        role.setName("-name");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameArgumentHasExtraDashesThenIAE() {
        role.setName("name-2-name-2n");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameArgumentHasIllegalSymbolFromStartThenIAE() {
        role.setName("&name");
    }

    @Test
    public void setNameIgnoreExtraWhiteSymbolsFromStartAndEnd() {
        role.setName(" name-2 ");
        assertThat(role.getName()).isEqualTo("NAME-2");
    }

    @Test
    public void setNameWithAlphabetAndNumbersOnly() {
        role.setName("name2");
        assertThat(role.getName()).isEqualTo("NAME2");
    }

    @Test
    public void setIdAndGetId() {
        role.setId(10);
        assertThat(role.getId()).isEqualTo(10);
    }

    @Test
    public void getNameReturnEmptyStringIfNameIsNull() {
        assertThat(new Role().getName()).isEqualTo("");
    }

    @Test
    public void toStringReturnRoleName() {
        role.setName("name23");
        assertThat(role.toString()).isEqualTo("NAME23");
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(Role.class)
                .withIgnoredFields("NAME")
                .usingGetClass().suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void getRoleNamePatternByValueOf() {
        assertThat(Role.Patterns.valueOf("NAME")).isSameAs(Role.Patterns.NAME);
    }

    @Test(expected = NullPointerException.class)
    public void whenValueOfNullThenNPE() {
        Role.Patterns.valueOf(null);
    }

    @Test
    public void checkToString() {
        assertThat(Role.Patterns.NAME.toString()).isEqualTo(Role.Patterns.NAME.getPattern().pattern());
    }
}