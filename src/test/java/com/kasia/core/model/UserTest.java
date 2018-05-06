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
    }

    // Serializable && CoreModel ----------------------
    @Test
    public void implementsSerializable() {
        Serializable ser = new User();
        assertThat(ser).isNotNull();

        CoreModel model = new User();
        assertThat(model).isNotNull();
    }

    // id -----------------------------------------------
    @Test
    public void setAndGetId() {
        user.setId(new Long(10));
        assertThat(user.getId()).isEqualTo(10L);
    }

    @Test
    public void setIdHasProtectedModifierAccess() throws NoSuchMethodException {
        Method setId = User.class.getDeclaredMethod("setId", Long.class);
        assertThat(Modifier.isProtected(setId.getModifiers())).isTrue();
    }


}