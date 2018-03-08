package com.kasia.core.model;

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

    //====================================SURNAME==============
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

    //==========FIRM======================
    @Test
    public void setFirm() {
        details.setFirm("Firm");
        assertThat(details.getFirm()).isEqualTo("Firm");
    }

    @Test
    public void setFirmNameWithUpperCase() {
        details.setFirm("FIRM");
        assertThat(details.getFirm()).isEqualTo("FIRM");
    }

    @Test
    public void setFirmNameWithLowerCase() {
        details.setFirm("firm32");
        assertThat(details.getFirm()).isEqualTo("firm32");
    }

    @Test
    public void setFirmNameIgnoreExtraWhiteSymbols() {
        details.setFirm("   Firm    firm   ");
        assertThat(details.getFirm()).isEqualTo("Firm firm");
    }

    @Test
    public void setCompositeFirmName() {
        details.setFirm("Firm-firm");
        assertThat(details.getFirm()).isEqualTo("Firm-firm");
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
        assertThat(details.getFirm()).isEqualTo("A+B Firm");
    }

    @Test
    public void setFirmNameWithAmpersant() {
        details.setFirm(" A&B Firm");
        assertThat(details.getFirm()).isEqualTo("A&B Firm");
    }

    @Test
    public void setFirmNameWithAtSign() {
        details.setFirm(" A@B Firm");
        assertThat(details.getFirm()).isEqualTo("A@B Firm");
    }

    @Test
    public void setFirmNameWithPoint() {
        details.setFirm(" A.B Firm");
        assertThat(details.getFirm()).isEqualTo("A.B Firm");
    }

    @Test
    public void setFirmNameWithApostrophe() {
        details.setFirm(" A'B Firm");
        assertThat(details.getFirm()).isEqualTo("A'B Firm");
    }

    @Test
    public void setFirmNameWithColon() {
        details.setFirm(" A:B Firm");
        assertThat(details.getFirm()).isEqualTo("A:B Firm");
    }

    @Test
    public void setFirmNameWithBracket() {
        details.setFirm("({A<>B} [Firm])");
        assertThat(details.getFirm()).isEqualTo("({A<>B} [Firm])");
    }

    @Test
    public void setFirmNameWithExclamationAndGuillemets() {
        details.setFirm("«A!B» Firm");
        assertThat(details.getFirm()).isEqualTo("«A!B» Firm");
    }

    @Test
    public void setFirmNameWithInvertedComma() {
        details.setFirm("“AB“ Firm");
        assertThat(details.getFirm()).isEqualTo("“AB“ Firm");
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

    //==========POSITION======================
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
        details.setPosition("position");
        assertThat(details.getPosition()).isEqualTo("POSITION");
    }

    @Test
    public void setPositionIgnoreExtraWhiteSymbols() {
        details.setPosition("   Position    ");
        assertThat(details.getPosition()).isEqualTo("POSITION");
    }

    @Test
    public void setCompositePosition() {
        details.setPosition("Position-position");
        assertThat(details.getPosition()).isEqualTo("POSITION-POSITION");
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
    public void whenSetPositionArgumentHasTwoWordThenIAE() {
        details.setPosition("Surna me");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetPositionArgumentHasNumberThenIAE() {
        details.setPosition("Surname32");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetPositionArgumentHasNotOnlyAlphabetSymbolsThenIAE() {
        details.setPosition("surname&@");
    }

    @Test
    public void whenSPositionNullGetterReturnEmptySttring() {
        assertThat(details.getPosition()).isEqualTo("");
    }
//==========================================
    @Test
    public void getDetailsOfNameSurnameFirmPosition() {
        Details details = new Details("NAME", "SURNAME", "FIRM NAME", "POSITION");
        assertThat(details.getName()).isEqualTo("NAME");
        assertThat(details.getSurname()).isEqualTo("SURNAME");
        assertThat(details.getFirm()).isEqualTo("FIRM NAME");
        assertThat(details.getPosition()).isEqualTo("POSITION");
    }

//    @Rule
//    public ExpectedException exception = ExpectedException.none();
//    private Details details;
//
//    @Before
//    public void before() {
//        details = new Details();
//    }
//
//    @Test
//    public void checkConstructions() {
//        details.setNickName("NickName");
//        details.setId(10L);
//        details.setName("nick");
//        details.setEmail("mail");
//        details.setSecondName("second");
//        Details details2 = new Details(details);
//        Assert.assertTrue(details.equals(details2));
//    }
//
//    @Test
//    public void setAndGetNickName() {
//        String nickName = "nickName";
//        details.setNickName(nickName);
//        Assert.assertEquals("set and get nickName", nickName, details.getNickName());
//    }
//
//    @Test
//    public void setAndGetSecondName() {
//        String secondName = "secondName";
//        details.setSecondName(secondName);
//        Assert.assertEquals("set and get secondName", secondName, details.getSecondName());
//    }
//
//    @Test
//    public void setAndGetName() {
//        String name = "name";
//        details.setName(name);
//        Assert.assertEquals("set and get name", name, details.getName());
//    }
//
//    @Test
//    public void setAndGetEMail() {
//        String eMail = "email";
//        details.setEmail(eMail);
//        Assert.assertEquals("set and get eMail", eMail, details.getEmail());
//    }
//
//    @Test
//    public void setAndGetId() {
//        Long id = 100L;
//        details.setId(id);
//        Assert.assertEquals("set and get id", id, details.getId());
//    }
//
//    @Test
//    public void checkEqualsAndHashCode() {
//        Details details1 = new Details(100L, "nickName", "secondName", "mail", "name");
//        Details details2 = new Details(100L, "nickName", "secondName", "mail", "name");
//        Assert.assertEquals("details1.equals(details2)", details1, details2);
//        Assert.assertEquals("details1.equals(details2)", details1.hashCode(), details2.hashCode());
//        details2.setName("testName");
//        Assert.assertNotEquals("details1.equals(details2)", details1, details2);
//        Assert.assertNotEquals("details1.equals(details2)", details1.hashCode(), details2.hashCode());
//    }
//
//    @Test
//    public void checkToString() {
//        String toString = "nickName name secondName mail";
//        details = new Details(100L, "nickName", "secondName", "mail", "name");
//        Assert.assertEquals(toString, details.toString());
//    }
//
//    @Test
//    public void whenSetNameArgumentNullThenNullPointerException() {
//        exception.expect(NullPointerException.class);
//        details.setName(null);
//    }
//
//    @Test
//    public void whenSetSecondNameArgumentNullThenNullPointerException() {
//        exception.expect(NullPointerException.class);
//        details.setSecondName(null);
//    }
//
//    @Test
//    public void whenSetNickNameArgumentNullThenNullPointerException() {
//        exception.expect(NullPointerException.class);
//        details.setNickName(null);
//    }
//
//    @Test
//    public void whenSetEmailArgumentNullThenNullPointerException() {
//        exception.expect(NullPointerException.class);
//        details.setEmail(null);
//    }
//
//    @Test
//    public void getNickNameNotNull() {
//        details = new Details();
//        Assert.assertNotNull(details.getNickName());
//    }
//
//    @Test
//    public void getSecondNameNotNull() {
//        details = new Details();
//        Assert.assertNotNull(details.getSecondName());
//    }
//
//    @Test
//    public void getEmailNotNull() {
//        details = new Details();
//        Assert.assertNotNull(details.getEmail());
//    }
//
//    @Test
//    public void getNameNotNull() {
//        details = new Details();
//        Assert.assertNotNull(details.getName());
//    }
//
//    @Test
//    public void setNickNameTrimWhiteSymbols() {
//        String nameSpaces = "   name   ";
//        String name = "name";
//        details = new Details();
//        details.setNickName(nameSpaces);
//        Assert.assertEquals(name, details.getNickName());
//    }
//
//    @Test
//    public void setNameTrimWhiteSymbols() {
//        String nameSpaces = "   name   ";
//        String name = "name";
//        details = new Details();
//        details.setName(nameSpaces);
//        Assert.assertEquals(name, details.getName());
//    }
//
//    @Test
//    public void setSecondNameTrimWhiteSymbols() {
//        String nameSpaces = "   name   ";
//        String name = "name";
//        details = new Details();
//        details.setSecondName(nameSpaces);
//        Assert.assertEquals(name, details.getSecondName());
//    }
//
//    @Test
//    public void setEmailTrimWhiteSymbols() {
//        String nameSpaces = "   name   ";
//        String name = "name";
//        details = new Details();
//        details.setEmail(nameSpaces);
//        Assert.assertEquals(name, details.getEmail());
//    }
}