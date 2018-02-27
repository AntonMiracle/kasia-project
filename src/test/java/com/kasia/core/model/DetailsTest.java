package com.kasia.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DetailsTest {
    private Details details;

    @Before
    public void before() {
        details = new Details();
    }

    @Test
    public void checkConstructions() {
        details.setName("name");
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
        Details details1 = new Details(100L, "name", "secondName", "nickName", "mail");
        Details details2 = new Details(100L, "name", "secondName", "nickName", "mail");
        Assert.assertEquals("details1.equals(details2)", details1, details2);
        Assert.assertEquals("details1.equals(details2)", details1.hashCode(), details2.hashCode());
        details2.setName("testName");
        Assert.assertNotEquals("details1.equals(details2)", details1, details2);
        Assert.assertNotEquals("details1.equals(details2)", details1.hashCode(), details2.hashCode());
    }

}