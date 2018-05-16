package com.kasia.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GroupTypeTest {
    private GroupType groupType;

    @Before
    public void before() {
        groupType = new GroupType();
    }

    // Serializable && CoreModel -----------------------------------------
    @Test
    public void groupTypeImplementsSerializable() {
        Serializable model = new GroupType();
        assertThat(model).isNotNull();
    }

    @Test
    public void groupTypeImplementsCoreModel() {
        CoreModel core = new GroupType();
        assertThat(core).isNotNull();
    }

    // id -----------------------------------------
    @Test
    public void setAndGetId() {
        groupType.setId(10L);
        assertThat(groupType.getId()).isEqualTo(10L);
    }

    @Test
    public void setIdHasProtectedModifierAccess() throws NoSuchMethodException {
        Method setId = GroupType.class.getDeclaredMethod("setId", long.class);
        assertThat(Modifier.isProtected(setId.getModifiers())).isTrue();
    }

    @Test
    public void defaultGetIdReturnZero() {
        groupType = new GroupType();
        assertThat(groupType.getId()).isEqualTo(0l);
    }

    // name -----------------------------------------
    @Test
    public void setAndGetName() {
        groupType.setName("name");
        assertThat(groupType.getName()).isEqualTo("name");
    }

    // null -----------------------------------------
    @Test
    public void setAndIsNull() {
        groupType.setNull(true);
        assertThat(groupType.isNull()).isTrue();
    }

    @Test
    public void defaultIsNullReturnFalse() {
        groupType = new GroupType();
        assertThat(groupType.isNull()).isFalse();
    }

    // valid -----------------------------------------
    @Test
    public void setAndIsValid() {
        groupType.setValid(true);
        assertThat(groupType.isValid()).isTrue();
    }

    @Test
    public void defaultIsValidReturnFalse() {
        groupType = new GroupType();
        assertThat(groupType.isValid()).isFalse();
    }

    // hashCode && equals -----------------------------------------
    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(GroupType.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}