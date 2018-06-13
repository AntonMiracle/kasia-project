package com.kasia.model.user;

import com.kasia.model.Model;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
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
    }

    // CONSTRUCTORS ================================================
    @Test
    public void constructorWithNoArgExist() {
        assertThat(new User()).isNotNull();
    }

    @Test
    public void constructorWithAllArgExist() {
        int actualSumClassFields = new User().getClass().getDeclaredFields().length;

        String username = "username";
        String password = "password";
        String email = "email@email.com";
        Set<String> group = new HashSet<>();
        Instant create = Instant.now();
        Locale locale = Locale.getDefault();
        ZoneId zoneId = ZoneId.systemDefault();
        int expectedSumClassFields = 7;

        assertThat(actualSumClassFields).isEqualTo(expectedSumClassFields);
        assertThat(new User(username, password, email, group, create, locale, zoneId)).isNotNull();
    }

    // ================================================
    @Test
    public void extendsModel() {
        assertThat(Model.class.isAssignableFrom(user.getClass())).isTrue();
    }

    @Test
    public void implementsSerializable() {
        assertThat(Serializable.class.isAssignableFrom(user.getClass())).isTrue();
    }

    @Test
    public void setAndGetUsername() {
        user.setUsername("Username");
        assertThat(user.getUsername()).isEqualTo("Username");
    }

    @Test
    public void setAndGetPassword() {
        user.setPassword("password");
        assertThat(user.getPassword()).isEqualTo("password");
    }

    @Test
    public void setAndGetGroups() {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        user.setGroups(roles);
        assertThat(user.getGroups()).isEqualTo(roles);
        assertThat(user.getGroups().size()).isEqualTo(2);
        assertThat(user.getGroups().contains("admin")).isTrue();
        assertThat(user.getGroups().contains("user")).isTrue();
    }

    @Test
    public void setAndGetEmail() {
        user.setEmail("email@email.com");
        assertThat(user.getEmail()).isEqualTo("email@email.com");
    }

    @Test
    public void setAndGetCreate() {
        Instant create = Instant.now();
        user.setCreate(create);
        assertThat(user.getCreate()).isEqualTo(create);
    }

    @Test
    public void setAndGetLocale() {
        user.setLocale(Locale.getDefault());
        assertThat(user.getLocale()).isEqualTo(Locale.getDefault());
    }

    @Test
    public void setAndGetZoneId() {
        user.setZoneId(ZoneId.systemDefault());
        assertThat(user.getZoneId()).isEqualTo(ZoneId.systemDefault());
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(user.getClass())
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void toStringIsOverride() {
        assertThat(user.toString().contains("{")).isTrue();
        assertThat(user.toString().contains(user.getClass().getSimpleName())).isTrue();
    }
}