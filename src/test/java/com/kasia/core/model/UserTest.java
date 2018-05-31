package com.kasia.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {
    private User user;

    @Before
    public void before() {
        user = new User();
        assert user instanceof Serializable;
        assert user instanceof Model;
    }

    // id ================================================
    @Test
    public void setAndGetId() {
        user = new User();
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

    // Username ================================================
    @Test
    public void setAndGetUsername() {
        user = new User();
        user.setUsername("Username");
        assertThat(user.getUsername()).isEqualTo("Username");
    }

    // password ================================================
    @Test
    public void setAndGetPassword() {
        user = new User();
        user.setPassword("pass");
        assertThat(user.getPassword()).isEqualTo("pass");

    }

    // email ================================================
    @Test
    public void setAndGetEmail() {
        user = new User();
        user.setEmail("mail");
        assertThat(user.getEmail()).isEqualTo("mail");
    }

    // create on ================================================
    @Test
    public void setAndGetCreateOn() {
        Instant now = Instant.now();
        user = new User();
        user.setCreateOn(now);
        assertThat(user.getCreateOn()).isEqualTo(now);
    }

    // Roles ================================================
    @Test
    public void setAndGetRoles() {
        user = new User();
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);
        assertThat(user.getRoles()).isEqualTo(roles);
    }

    @Test
    public void defaultRolesReturnNull() {
        user = new User();
        assertThat(user.getRoles()).isNull();
    }

    // Locale ================================================
    @Test
    public void setAndGetLocale() {
        Locale locale = Locale.getDefault();
        user = new User();
        user.setLocale(locale);
        assertThat(user.getLocale()).isEqualTo(locale);
    }

    // ZoneId ================================================
    @Test
    public void setAndGetZoneId() {
        ZoneId zone = ZoneId.systemDefault();
        user = new User();
        user.setZoneId(zone);
        assertThat(user.getZoneId()).isEqualTo(zone);
    }
    // Equals && HashCode ================================================
    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(User.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}