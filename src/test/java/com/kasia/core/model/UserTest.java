package com.kasia.core.model;

import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {
    private User user;

    @Before
    public void before() {
        user = new User();
        assert user instanceof Serializable;
        assert user instanceof Model;
    }

    // id -----------------------------------------------
    @Test
    public void setAndGetId() {
        user.setId(new Long(10));
        assertThat(user.getId()).isEqualTo(10L);
    }

    @Test
    public void setIdHasProtectedModifierAccess() throws NoSuchMethodException {
        Method setId = User.class.getDeclaredMethod("setId", long.class);
        assertThat(Modifier.isProtected(setId.getModifiers())).isTrue();
    }

    @Test
    public void defaultGetIdReturnZero() {
        user = new User();
        assertThat(user.getId()).isEqualTo(0l);
    }

}