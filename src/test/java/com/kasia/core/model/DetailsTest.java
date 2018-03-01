package com.kasia.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DetailsTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private Details details;

    @Before
    public void before() {
        details = new Details();
    }

    @Test
    public void checkConstructions() {
        details.setNickName("NickName");
        details.setId(10L);
        details.setName("nick");
        details.setEmail("mail");
        details.setSecondName("second");
        Details details2 = new Details(details);
        Assert.assertTrue(details.equals(details2));
    }

    @Test
    public void setAndGetNickName() {
        String nickName = "nickName";
        details.setNickName(nickName);
        Assert.assertEquals("set and get nickName", nickName, details.getNickName());
    }

    @Test
    public void setAndGetSecondName() {
        String secondName = "secondName";
        details.setSecondName(secondName);
        Assert.assertEquals("set and get secondName", secondName, details.getSecondName());
    }

    @Test
    public void setAndGetName() {
        String name = "name";
        details.setName(name);
        Assert.assertEquals("set and get name", name, details.getName());
    }

    @Test
    public void setAndGetEMail() {
        String eMail = "email";
        details.setEmail(eMail);
        Assert.assertEquals("set and get eMail", eMail, details.getEmail());
    }

    @Test
    public void setAndGetId() {
        Long id = 100L;
        details.setId(id);
        Assert.assertEquals("set and get id", id, details.getId());
    }

    @Test
    public void checkEqualsAndHashCode() {
        Details details1 = new Details(100L, "nickName", "secondName", "mail", "name");
        Details details2 = new Details(100L, "nickName", "secondName", "mail", "name");
        Assert.assertEquals("details1.equals(details2)", details1, details2);
        Assert.assertEquals("details1.equals(details2)", details1.hashCode(), details2.hashCode());
        details2.setName("testName");
        Assert.assertNotEquals("details1.equals(details2)", details1, details2);
        Assert.assertNotEquals("details1.equals(details2)", details1.hashCode(), details2.hashCode());
    }

    @Test
    public void checkToString() {
        String toString = "nickName name secondName mail";
        details = new Details(100L, "nickName", "secondName", "mail", "name");
        Assert.assertEquals(toString, details.toString());
    }

    @Test
    public void whenSetNameArgumentNullThenNullPointerException() {
        exception.expect(NullPointerException.class);
        details.setName(null);
    }

    @Test
    public void whenSetSecondNameArgumentNullThenNullPointerException() {
        exception.expect(NullPointerException.class);
        details.setSecondName(null);
    }

    @Test
    public void whenSetNickNameArgumentNullThenNullPointerException() {
        exception.expect(NullPointerException.class);
        details.setNickName(null);
    }

    @Test
    public void whenSetEmailArgumentNullThenNullPointerException() {
        exception.expect(NullPointerException.class);
        details.setEmail(null);
    }

    @Test
    public void getNickNameNotNull() {
        details = new Details();
        Assert.assertNotNull(details.getNickName());
    }

    @Test
    public void getSecondNameNotNull() {
        details = new Details();
        Assert.assertNotNull(details.getSecondName());
    }

    @Test
    public void getEmailNotNull() {
        details = new Details();
        Assert.assertNotNull(details.getEmail());
    }

    @Test
    public void getNameNotNull() {
        details = new Details();
        Assert.assertNotNull(details.getName());
    }

    @Test
    public void setNickNameTrimWhiteSymbols() {
        String nameSpaces = "   name   ";
        String name = "name";
        details = new Details();
        details.setNickName(nameSpaces);
        Assert.assertEquals(name, details.getNickName());
    }

    @Test
    public void setNameTrimWhiteSymbols() {
        String nameSpaces = "   name   ";
        String name = "name";
        details = new Details();
        details.setName(nameSpaces);
        Assert.assertEquals(name, details.getName());
    }

    @Test
    public void setSecondNameTrimWhiteSymbols() {
        String nameSpaces = "   name   ";
        String name = "name";
        details = new Details();
        details.setSecondName(nameSpaces);
        Assert.assertEquals(name, details.getSecondName());
    }

    @Test
    public void setEmailTrimWhiteSymbols() {
        String nameSpaces = "   name   ";
        String name = "name";
        details = new Details();
        details.setEmail(nameSpaces);
        Assert.assertEquals(name, details.getEmail());
    }
}