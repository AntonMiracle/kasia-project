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

    // Serializable && CoreModel ----------------------
    @Test
    public void roleImplementsSerializable() {
        Serializable ser = new Role();
        assertThat(ser).isNotNull();

        CoreModel model = new Role();
        assertThat(model).isNotNull();
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

    // null -----------------------------------------
    @Test
    public void setAndIsNull() {
        role.setNull(true);
        assertThat(role.isNull()).isTrue();
    }

    @Test
    public void defaultIsNullReturnFalse() {
        role = new Role();
        assertThat(role.isNull()).isFalse();
    }

    // valid -----------------------------------------
    @Test
    public void setAndIsValid() {
        role.setValid(true);
        assertThat(role.isValid()).isTrue();
    }

    @Test
    public void defaultIsValidReturnFalse() {
        role = new Role();
        assertThat(role.isValid()).isFalse();
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