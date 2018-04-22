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
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {
    private User user;

    @Before
    public void before() {
        user = new User();
    }

    @Test
    public void setAndGetId() {
        user.setId(new Long(10L));
        assertThat(user.getId()).isEqualTo(10L);
    }

    @Test
    public void setIdHasProtectedModifierAccess() throws NoSuchMethodException {
        Method setId = User.class.getDeclaredMethod("setId", Long.class);
        assertThat(Modifier.isProtected(setId.getModifiers())).isTrue();
    }

    @Test
    public void detailsImplementsSerializable() {
        Serializable serializable = new User();
        assertThat(serializable).isNotNull();
    }

    @Test
    public void setAndGetDetails() {
        user.setDetails(new Details());
        assertThat(user.getDetails().getClass()).isEqualTo(Details.class);
    }

    @Test
    public void setAndGetLogin() {
        user.setLogin("login");
        assertThat(user.getLogin()).isEqualTo("login");
    }

    @Test
    public void setAndGetPassword() {
        user.setPassword("pass");
        assertThat(user.getPassword()).isEqualTo("pass");
    }

    @Test
    public void setAndGetCreateOn() {
        Instant now = Instant.now();
        user.setCreateOn(now);
        assertThat(user.getCreateOn()).isEqualTo(now);
    }

    @Test
    public void setAndGetLocale() {
        Locale locale = Locale.getDefault();
        user.setLocale(locale);
        assertThat(user.getLocale()).isEqualTo(locale);
    }

    @Test
    public void setAndGetZoneId() {
        ZoneId id = ZoneId.systemDefault();
        user.setZoneId(id);
        assertThat(user.getZoneId()).isEqualTo(id);
    }

    @Test
    public void getUserFromAnotherUser() {
        User user2 = new User();
        user2.setPassword("pass");
        user2.setZoneId(ZoneId.systemDefault());
        user2.setLocale(Locale.getDefault());
        user2.setLogin("login");
        user2.setDetails(new Details());
        user2.setCreateOn(Instant.now());
        user2.setId(10L);

        user = new User(user2);

        assertThat(user.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user.getZoneId()).isEqualTo(user2.getZoneId());
        assertThat(user.getLocale()).isEqualTo(user2.getLocale());
        assertThat(user.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user.getDetails()).isEqualTo(user2.getDetails());
        assertThat(user.getCreateOn()).isEqualTo(user2.getCreateOn());
        assertThat(user.getId()).isEqualTo(user2.getId());
    }

    @Test
    public void userInCloneConstructionUseCloneConstructionForDetails() {
        Details details = new Details();
        details.setName("name");
        user.setDetails(details);

        user = new User(user);

        assertThat(user.getDetails().getName()).isEqualTo(details.getName());
        details.setName("anotherName");
        assertThat(user.getDetails().getName()).isNotEqualTo(details.getName());
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(User.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}