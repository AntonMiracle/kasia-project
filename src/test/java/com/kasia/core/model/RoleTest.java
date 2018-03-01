package com.kasia.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RoleTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private Role role;

    @Before
    public void before() {
        role = new Role();
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
        role = new Role(10L, "role1");
        Role role2 = new Role(10L, "role1");
        Assert.assertTrue(role.equals(role2));
        Assert.assertTrue(role.hashCode() == role2.hashCode());
        role2.setId(11L);
        Assert.assertFalse(role.equals(role2));
        Assert.assertFalse(role.hashCode() == role2.hashCode());
    }

    @Test
    public void checkConstructions() {
        Assert.assertNotNull("check no arg", new Role());
        String roleName = "role";
        Role role = new Role(roleName);
        Assert.assertNotNull("check with role name", role);
        Assert.assertEquals(roleName, role.getName());
        Role role2 = new Role(role);
        Assert.assertTrue("check with role", role.equals(role2));
    }

    @Test
    public void getNameNotNull() {
        role = new Role();
        Assert.assertNotNull(role.getName());

    }

    @Test
    public void whenSetNameArgumentNullThenNullPointerException() {
        exception.expect(NullPointerException.class);
        role.setName(null);
    }

    @Test
    public void checkToString() {
        String name = "name";
        role.setName(name);
        Assert.assertEquals(name, role.toString());
    }

    @Test
    public void setNameTrimWhiteSymbols() {
        String nameWithSpaces = "    name   ";
        String name = "name";
        role.setName(nameWithSpaces);
        Assert.assertNotEquals(nameWithSpaces, role.getName());
        Assert.assertEquals(name, role.getName());
    }

}