package com.kasia.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class RoleTest {
    private Role role;

    @Before
    public void before() {
        role = new Role();
        assert role instanceof Serializable;
        assert role instanceof Model;
    }

    // id -----------------------------------------------
    @Test
    public void setAndGetId() {
        role.setId(new Long(10));
        assertThat(role.getId()).isEqualTo(10L);
    }

    @Test
    public void setIdHasProtectedModifierAccess() throws NoSuchMethodException {
        Method setId = Role.class.getDeclaredMethod("setId", long.class);
        assertThat(Modifier.isProtected(setId.getModifiers())).isTrue();
    }

    @Test
    public void defaultGetIdReturnZero() {
        role = new Role();
        assertThat(role.getId()).isEqualTo(0l);
    }

    //name --------------------------------------
    @Test
    public void setAndGetName() {
        role.setName("Name");
        assertThat(role.getName()).isEqualTo("Name");
    }

    // hashCode && equals -------------------------------------
    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(Role.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}