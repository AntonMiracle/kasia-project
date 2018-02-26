package com.kasia.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UlapTest {
    private Ulap uLap;

    @Before
    public void before() {
        uLap = new Ulap();
    }

    @Test
    public void checkConstructions() {
        uLap = new Ulap();
        Assert.assertNotNull(uLap);
        Assert.assertFalse(uLap.isCrypt());
        uLap = new Ulap("login", "password");
        Assert.assertNotNull(uLap);
        Assert.assertFalse(uLap.isCrypt());
    }

    @Test
    public void setAndGetPassword() {
        String password = "password";
        uLap.setPassword(password);
        Assert.assertNotNull(uLap.getPassword());
        Assert.assertEquals("get MD5 representation", password, uLap.getPassword());
    }

    @Test
    public void setAndGetLogin() {
        String login = "login";
        uLap.setLogin(login);
        Assert.assertNotNull(uLap.getLogin());
        Assert.assertEquals("get MD5 representation", login, uLap.getLogin());
    }

    @Test
    public void setAndGetId() {
        Long id = 100L;
        uLap.setId(id);
        Assert.assertEquals("set and get id", id, uLap.getId());
    }

    @Test
    public void checkEqualsAndHashCode() {
        Ulap ulap1 = creatUlap(10L, "login", "password");
        Ulap ulap2 = creatUlap(10L, "login", "password");
        Ulap ulap3 = creatUlap(10L, "login1", "password");
        ulap1.crypt();
        ulap2.crypt();
        ulap3.crypt();
        Assert.assertTrue("ulap1.equals(ulap2)", ulap1.equals(ulap2));
        Assert.assertFalse("ulap2.equals(ulap3)", ulap2.equals(ulap3));
    }

    @Test
    public void isPasswordEquals() {
        String password = "password";
        uLap = creatUlap(100L, "login", password);
        uLap.crypt();
        Assert.assertTrue("is password correct", uLap.isPasswordEquals(password));
        Assert.assertFalse("is password incorrect", uLap.isPasswordEquals("wrong_password"));
    }

    @Test
    public void cryptUlap() {
        uLap = creatUlap(1L, "login", "password");
        Assert.assertFalse(uLap.isCrypt());
        Assert.assertTrue(uLap.crypt());
        Assert.assertTrue(uLap.isCrypt());
    }

    private Ulap creatUlap(Long id, String login, String password) {
        Ulap uLap = new Ulap();
        uLap.setId(id);
        uLap.setLogin(login);
        uLap.setPassword(password);
        return uLap;
    }
}