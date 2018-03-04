package com.kasia.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SecurityTest extends Security{
    private Security security;

    @Before
    public void before() {
        security = new Security();
    }

    @Test
    public void checkConstructions() {
        security = new Security();
        Assert.assertNotNull(security);
        Assert.assertFalse(security.isCrypt());
        security = new Security("login", "password");
        Assert.assertNotNull(security);
        Assert.assertTrue(security.isCrypt());
    }

    @Test
    public void setAndGetPassword() {
        String password = "password";
        security.setPassword(password);
        Assert.assertNotNull(security.getPassword());
        Assert.assertEquals("get MD5 representation", password, security.getPassword());
    }

    @Test
    public void setAndGetLogin() {
        String login = "login";
        security.setLogin(login);
        Assert.assertNotNull(security.getLogin());
        Assert.assertEquals("get MD5 representation", login, security.getLogin());
    }

    @Test
    public void setAndGetId() {
        Long id = 100L;
        security.setId(id);
        Assert.assertEquals("set and get id", id, security.getId());
    }

    @Test
    public void checkEqualsAndHashCode() {
        Security security1 = new Security(10L, "login", "password");
        Security security2 = new Security(10L, "login", "password");
        Security security3 = new Security(10L, "login1", "password");
        security1.crypt();
        security2.crypt();
        security3.crypt();
        Assert.assertTrue("security1.equals(security2)", security1.equals(security2));
        Assert.assertFalse("security2.equals(security3)", security2.equals(security3));
    }

    @Test
    public void isPasswordEqualsCryptPassword() {
        String password = "password";
        security = new Security(100L, "login", password);
        security.crypt();
        Assert.assertTrue("is password correct", security.isPasswordEquals(password));
        Assert.assertFalse("is password incorrect", security.isPasswordEquals("wrong_password"));
    }

    @Test
    public void cryptSecurity() {
        security = new Security(1L, "login", "password");
        Assert.assertFalse(security.isCrypt());
        Assert.assertTrue(security.crypt());
        Assert.assertTrue(security.isCrypt());
    }

}