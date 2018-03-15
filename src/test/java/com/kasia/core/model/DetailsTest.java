package com.kasia.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DetailsTest {
    private Details details;

    @Before
    public void before() {
        details = new Details();
    }

    @Test
    public void setName() {
        details.setName("Name");
        assertThat(details.getName()).isEqualTo("NAME");
    }

    @Test
    public void setNameWithUpperCase() {
        details.setName("NAME");
        assertThat(details.getName()).isEqualTo("NAME");
    }

    @Test
    public void setNameWithLowerCase() {
        details.setName("name");
        assertThat(details.getName()).isEqualTo("NAME");
    }

    @Test
    public void setNameIgnoreExtraWhiteSymbols() {
        details.setName("   Name    ");
        assertThat(details.getName()).isEqualTo("NAME");
    }

    @Test
    public void setNameWithWhiteSymbols() {
        details.setName("    ");
        assertThat(details.getName()).isEqualTo("");
    }

    @Test
    public void setNameWithEmptyString() {
        details.setName("");
        assertThat(details.getName()).isEqualTo("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameWithNullThenIAE() {
        details.setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameArgumentHasTwoWordThenIAE() {
        details.setName("na me");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameArgumentHasNumberThenIAE() {
        details.setName("name32");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetNameArgumentHasNotOnlyAlphabetSymbolsThenIAE() {
        details.setName("name&@");
    }

    @Test
    public void whenNameNullGetterReturnEmptySttring() {
        assertThat(details.getName()).isEqualTo("");
    }

    @Test
    public void setSurname() {
        details.setSurname("Surname");
        assertThat(details.getSurname()).isEqualTo("SURNAME");
    }

    @Test
    public void setSurnameWithUpperCase() {
        details.setSurname("SURNAME");
        assertThat(details.getSurname()).isEqualTo("SURNAME");
    }

    @Test
    public void setSurnameWithLowerCase() {
        details.setSurname("surname");
        assertThat(details.getSurname()).isEqualTo("SURNAME");
    }

    @Test
    public void setSurnameIgnoreExtraWhiteSymbols() {
        details.setSurname("   Surname    ");
        assertThat(details.getSurname()).isEqualTo("SURNAME");
    }

    @Test
    public void setCompositeSurname() {
        details.setSurname("Surname-Surname");
        assertThat(details.getSurname()).isEqualTo("SURNAME-SURNAME");
    }

    @Test
    public void setSurnameWithWhiteSymbols() {
        details.setSurname("    ");
        assertThat(details.getSurname()).isEqualTo("");
    }

    @Test
    public void setSurnameWithEmptyString() {
        details.setSurname("");
        assertThat(details.getSurname()).isEqualTo("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetSurnameWithNullThenIAE() {
        details.setSurname(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetSurnameArgumentHasTwoWordThenIAE() {
        details.setSurname("Surna me");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetSurnameArgumentHasNumberThenIAE() {
        details.setSurname("Surname32");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetSurnameArgumentHasNotOnlyAlphabetSymbolsThenIAE() {
        details.setSurname("surname&@");
    }

    @Test
    public void whenSurnameNullGetterReturnEmptySttring() {
        assertThat(details.getSurname()).isEqualTo("");
    }

    @Test
    public void setFirm() {
        details.setFirm("Firm");
        assertThat(details.getFirm()).isEqualTo("FIRM");
    }

    @Test
    public void setFirmNameWithUpperCase() {
        details.setFirm("FIRM");
        assertThat(details.getFirm()).isEqualTo("FIRM");
    }

    @Test
    public void setFirmNameWithLowerCase() {
        details.setFirm("firm32");
        assertThat(details.getFirm()).isEqualTo("FIRM32");
    }

    @Test
    public void setFirmNameIgnoreExtraWhiteSymbols() {
        details.setFirm("   Firm    firm   ");
        assertThat(details.getFirm()).isEqualTo("FIRM FIRM");
    }

    @Test
    public void setCompositeFirmName() {
        details.setFirm("Firm-firm");
        assertThat(details.getFirm()).isEqualTo("FIRM-FIRM");
    }

    @Test
    public void setFirmNameWithWhiteSymbols() {
        details.setFirm("    ");
        assertThat(details.getFirm()).isEqualTo("");
    }

    @Test
    public void setFirmNameWithEmptyString() {
        details.setFirm("");
        assertThat(details.getFirm()).isEqualTo("");
    }

    @Test
    public void setFirmNameWithPlus() {
        details.setFirm(" A+B Firm");
        assertThat(details.getFirm()).isEqualTo("A+B FIRM");
    }

    @Test
    public void setFirmNameWithAmpersant() {
        details.setFirm(" A&B Firm");
        assertThat(details.getFirm()).isEqualTo("A&B FIRM");
    }

    @Test
    public void setFirmNameWithAtSign() {
        details.setFirm(" A@B Firm");
        assertThat(details.getFirm()).isEqualTo("A@B FIRM");
    }

    @Test
    public void setFirmNameWithPoint() {
        details.setFirm(" A.B Firm");
        assertThat(details.getFirm()).isEqualTo("A.B FIRM");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetFirmWithNullThenIAE() {
        details.setFirm(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetFirmArgumentHasSpecialSymbolsThenIAE() {
        details.setFirm("firm\\/");
    }

    @Test
    public void whenFirmNullGetterReturnEmptyString() {
        assertThat(details.getFirm()).isEqualTo("");
    }

    @Test
    public void setPosition() {
        details.setPosition("Position");
        assertThat(details.getPosition()).isEqualTo("POSITION");
    }

    @Test
    public void setPositionWithUpperCase() {
        details.setPosition("POSITION");
        assertThat(details.getPosition()).isEqualTo("POSITION");
    }

    @Test
    public void setPositionWithLowerCase() {
        details.setPosition("position32");
        assertThat(details.getPosition()).isEqualTo("POSITION32");
    }

    @Test
    public void setPositionIgnoreExtraWhiteSymbols() {
        details.setPosition("   Position&    ");
        assertThat(details.getPosition()).isEqualTo("POSITION&");
    }

    @Test
    public void setCompositePositionWithDesh() {
        details.setPosition("Position-position");
        assertThat(details.getPosition()).isEqualTo("POSITION-POSITION");
    }

    @Test
    public void setCompositePositionWithWhiteSymbol() {
        details.setPosition("Position position");
        assertThat(details.getPosition()).isEqualTo("POSITION POSITION");
    }

    @Test
    public void setPositionWithWhiteSymbols() {
        details.setPosition("    ");
        assertThat(details.getPosition()).isEqualTo("");
    }

    @Test
    public void setPositionWithEmptyString() {
        details.setPosition("");
        assertThat(details.getPosition()).isEqualTo("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetPositionWithNullThenIAE() {
        details.setPosition(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetPositionArgumentHasSpecialSymbolsThenIAE() {
        details.setPosition("Position@");
    }

    @Test
    public void whenPositionNullGetterReturnEmptyString() {
        assertThat(details.getPosition()).isEqualTo("");
    }

    @Test
    public void setId() {
        details.setId(100);
        assertThat(details.getId()).isEqualTo(100);
    }

    @Test
    public void getDetailsOfNameSurnameFirmPosition() {
        details = new Details("NAME", "SURNAME", "FIRM NAME", "POSITION");
        assertThat(details.getName()).isEqualTo("NAME");
        assertThat(details.getSurname()).isEqualTo("SURNAME");
        assertThat(details.getFirm()).isEqualTo("FIRM NAME");
        assertThat(details.getPosition()).isEqualTo("POSITION");
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(Details.class)
                .withIgnoredFields("NAME", "SURNAME", "FIRM", "POSITION")
                .usingGetClass().suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void checkToString() {
        Details details = new Details("NAME", "SURNAME", "FIRM NAME", "POSITION");
        details.setId(10);
        assertThat(details.toString()).isEqualTo("10:NAME:SURNAME:FIRM NAME:POSITION");
    }

    @Test
    public void checkToStringWithEmptyString() {
        Details details = new Details();
        assertThat(details.toString()).isEqualTo("0::::");
    }

    @Test
    public void checkValueOf() {
        assertThat(Details.Patterns.valueOf("NAME").toString()).isEqualTo(Details.Patterns.NAME.toString());
        assertThat(Details.Patterns.valueOf("SURNAME").toString()).isEqualTo(Details.Patterns.SURNAME.toString());
        assertThat(Details.Patterns.valueOf("FIRM").toString()).isEqualTo(Details.Patterns.FIRM.toString());
        assertThat(Details.Patterns.valueOf("POSITION").toString()).isEqualTo(Details.Patterns.POSITION.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNameGreaterMaxLengthThenIAE() {
        details.setName("AntonAntonAntonAntonAntonAntonAAA");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSurnameGreaterMaxLengthThenIAE() {
        details.setSurname("AntonAntonAntonAntonAntonAntonAAA");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFirmGreaterMaxLengthThenIAE() {
        details.setFirm("AntonAntonAntonAntonAntonAntonAAA");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPositionGreaterMaxLengthThenIAE() {
        details.setPosition("AntonAntonAntonAntonAntonAntonAAA");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenErrorMsgWithNullPatterns() {
        details.errorMsgWithPatterns(null, "text");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenErrorMsgWithNullText() {
        details.errorMsgWithPatterns(Details.Patterns.NAME, null);
    }

    @Test
    public void getDetailsFromAnotherDetails() {
        details = new Details("NAME", "SURNAME", "FIRM NAME", "POSITION");
        assertThat(details).isEqualTo(new Details(details));
    }
}