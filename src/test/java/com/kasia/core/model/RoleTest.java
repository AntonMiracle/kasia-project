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
    }

    @Test
    public void setAndGetName() {
        role.setName("Role");
        assertThat(role.getName()).isEqualTo("Role");
    }

    @Test
    public void setAndGetId() {
        role.setId(new Long(10));
        assertThat(role.getId()).isEqualTo(10L);
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(Role.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void detailsImplementsSerializable() {
        Serializable serializable = new Role();
        assertThat(serializable).isNotNull();
    }

    @Test
    public void setIdHasProtectedModifierAccess() throws NoSuchMethodException {
        Method setId = Role.class.getDeclaredMethod("setId", Long.class);
        assertThat(Modifier.isProtected(setId.getModifiers())).isTrue();
    }

    @Test
    public void getROleFromAnotherRole() {
        Role role2 = new Role();
        role2.setId(10L);
        role2.setName("Role");
        role = new Role(role2);
        assertThat(role.getName()).isEqualTo(role2.getName());
        assertThat(role.getId()).isEqualTo(role2.getId());
    }

}