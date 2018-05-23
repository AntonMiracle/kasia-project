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
        assert groupType instanceof Serializable;
        assert groupType instanceof Model;
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

    // hashCode && equals -----------------------------------------
    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(GroupType.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}