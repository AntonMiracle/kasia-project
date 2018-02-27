package com.kasia.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class UserTest {
    private User user;

    @Before
    public void before() {
        user = new User();
    }

    @Test
    public void setAndGetSecurity() {
        String login = "login";
        String password = "password";
        Security uLap = new Security(login, password);
        user.setSecurity(uLap);
        Assert.assertNotNull("set and get Security", user.getSecurity());
        Assert.assertEquals("check Security#password", password, user.getSecurity().getPassword());
        Assert.assertEquals("check Security#login", login, user.getSecurity().getLogin());
    }

    @Test
    public void setAndGetCreateOn() {
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
    public void setAndGetDetails() {
        Details details = new Details();
        Assert.assertNull("set and get User#details", user.getDetails());
        user.setDetails(details);
        Assert.assertNotNull("set and get User#details", user.getDetails());
    }

    @Test
    public void addRole() {
        Role role1 = new Role(11L, "role1");
        Role role2 = new Role(211L, "role21");
        Role role3 = new Role(211L, "role21");
        Role role4 = new Role(null, "role21");
        Assert.assertFalse(user.isHas(role1));
        Assert.assertTrue("try add role1", user.addRole(role1));
        Assert.assertTrue(user.isHas(role1));
        Assert.assertFalse(user.isHas(role2));
        Assert.assertTrue("try add role2", user.addRole(role2));
        Assert.assertTrue(user.isHas(role2));
        Assert.assertTrue(user.isHas(role3));
        Assert.assertFalse(user.isHas(role4));
    }

    @Test
    public void removeRole() {
        Role role = new Role(10L, "role");
        Assert.assertFalse(user.isHas(role));
        Assert.assertFalse("try remove role", user.removeRole(role));
        Assert.assertTrue(user.addRole(role));
        Assert.assertTrue(user.isHas(role));
        Assert.assertTrue("try remove role", user.removeRole(role));
        Assert.assertFalse(user.isHas(role));
    }

    @Test
    public void getRoles() {
        Role role1 = new Role("role1");
        Role role21 = new Role("role21");
        Assert.assertNotNull(user.getRoles());
        Assert.assertTrue(user.addRole(role1));
        Assert.assertTrue(user.addRole(role21));
        Assert.assertTrue(user.getRoles().size() == 2);
    }

    @Test
    public void checkSetRolesForImutable() {
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);
        Assert.assertTrue(user.getRoles().size() == 0);
        roles.add(new Role("role"));
        Assert.assertTrue(user.getRoles().size() == 0);
    }

    @Test
    public void checkSetDetailsForImutable() {
        Details details = new Details();
        details.setName("name1");
        user.setDetails(details);
        Assert.assertEquals(details.getName(), user.getDetails().getName());
        details.setName("name2");
        Assert.assertNotEquals(details.getName(), user.getDetails().getName());
    }

    @Test
    public void checkAddRoleForImutable() {
        Role role = new Role(10L, "role");
        user.addRole(role);
        Assert.assertTrue(user.isHas(role));
        role.setName("role2");
        for (Role currentRole : user.getRoles()) {
            Assert.assertFalse(currentRole.getName().equals(role.getName()));
        }

    }

}