package com.kasia.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class UserTest {
    private User user;

    @Before
    public void before() {
        user = new User();
    }

    @Test
    public void setAndGetUlap() {
        String login = "login";
        String password = "password";
        Ulap uLap = new Ulap(login, password);
        user.setUlap(uLap);
        Assert.assertNotNull("set and get Ulap", user.getUlap());
        Assert.assertEquals("check Ulap#password", password, user.getUlap().getPassword());
        Assert.assertEquals("check Ulap#login", login, user.getUlap().getLogin());
    }

    @Test
    public void setAndGetCreateOnLocalDateTime() {
        LocalDateTime createOn = LocalDateTime.now();
        user.setCreateOn(createOn);
        Assert.assertEquals("set and get createOn using Timestamp.class", createOn, user.getCreateOn());
    }

    @Test
    public void setAndGetId() {
        Long id = 1000L;
        user.setId(id);
        Assert.assertEquals("set and get id", id, user.getId());
    }

    @Test
    public void setAndGetUinfo() {
        Uinfo uInfo = new Uinfo();
        Assert.assertNull("set and get User#uInfo",user.getInfo());
        user.setInfo(uInfo);
        Assert.assertNotNull("set and get User#uInfo",user.getInfo());

    }
    @Test
    public void addURole() {
        Urole uRole1 = createNewRole(11L, "uRole1");
        Urole uRole2 = createNewRole(211L, "role21");
        Urole uRole3 = createNewRole(211L, "role21");
        Urole uRole4 = createNewRole(null, "role21");
        Assert.assertFalse(user.isHas(uRole1));
        Assert.assertTrue("try add role1", user.addRole(uRole1));
        Assert.assertTrue(user.isHas(uRole1));
        Assert.assertFalse(user.isHas(uRole2));
        Assert.assertTrue("try add role2", user.addRole(uRole2));
        Assert.assertTrue(user.isHas(uRole2));
        Assert.assertTrue(user.isHas(uRole3));
        Assert.assertFalse(user.isHas(uRole4));
    }

    @Test
    public void removeUrole() {
        Urole uRole = createNewRole(10L, "uRole");
        Assert.assertFalse(user.isHas(uRole));
        Assert.assertFalse("try remove role", user.removeRole(uRole));
        Assert.assertTrue(user.addRole(uRole));
        Assert.assertTrue(user.isHas(uRole));
        Assert.assertTrue("try remove role", user.removeRole(uRole));
        Assert.assertFalse(user.isHas(uRole));
    }

    private Urole createNewRole(Long id, String name) {
        Urole uRole = new Urole(name);
        uRole.setId(id);
        return uRole;
    }
}