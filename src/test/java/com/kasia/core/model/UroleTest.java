package com.kasia.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UroleTest {
    private Urole role;

    @Before
    public void before() {
        role = new Urole();
    }

    @Test
    public void setAndGetName() {
        String name = "name";
        role.setName(name);
        Assert.assertEquals("set and get name", name, role.getName());
    }

    @Test
    public void setAndGetId() {
        Long id = 10L;
        role.setId(id);
        Assert.assertEquals("set and get id", id, role.getId());
    }

    @Test
    public void checkEqualsAndHashCodeMethod() {
        role = createNewRole(10L, "role1");
        Urole role2 = createNewRole(10L, "role1");
        Assert.assertTrue(role.equals(role2));
        Assert.assertTrue(role.hashCode() == role2.hashCode());
        role2.setId(11L);
        Assert.assertFalse(role.equals(role2));
        Assert.assertFalse(role.hashCode() == role2.hashCode());
    }

    @Test
    public void checkConstructions() {
        Assert.assertNotNull("check no arg",new Urole());
        String uRoleName = "role";
        Urole uRole = new Urole(uRoleName);
        Assert.assertNotNull("check with uRole name",uRole);
        Assert.assertEquals(uRoleName,uRole.getName());
    }

    private Urole createNewRole(Long id, String name) {
        Urole uRole = new Urole(name);
        uRole.setId(id);
        return uRole;
    }
}