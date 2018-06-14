package com.kasia.model.user;

import com.kasia.model.Model;
import com.kasia.model.group.Group;
import com.kasia.model.group.Type;
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
        int actualSumClassFields = user.getClass().getDeclaredFields().length;

        String username = "username";
        String password = "password";
        String email = "email@email.com";
        Set<Group> groups = new HashSet<>();
        Instant create = Instant.now();
        Locale locale = Locale.getDefault();
        ZoneId zoneId = ZoneId.systemDefault();
        int expectedSumClassFields = 7;

        assertThat(actualSumClassFields).isEqualTo(expectedSumClassFields);
        assertThat(new User(username, password, email, groups, create, locale, zoneId)).isNotNull();
    }

    // IMPLEMENTS EXTENDS HASHCODE EQUALS TO_STRING ================================================
    @Test
    public void extendsModel() {
        assertThat(Model.class.isAssignableFrom(user.getClass())).isTrue();
    }

    @Test
    public void implementsSerializable() {
        assertThat(Serializable.class.isAssignableFrom(user.getClass())).isTrue();
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(user.getClass())
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .withPrefabValues(Group.class, new Group(), new Group("name",new User(), Type.SYSTEM))
                .verify();
    }

    @Test
    public void toStringIsOverride() {
        assertThat(user.toString().contains("{")).isTrue();
        assertThat(user.toString().contains(user.getClass().getSimpleName())).isTrue();
    }

    // GETTERS SETTERS ================================================
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
        Group group = new Group();

        Set<Group> groups = new HashSet<>();
        groups.add(group);
        user.setGroups(groups);

        assertThat(user.getGroups()).isEqualTo(groups);
        assertThat(user.getGroups().size()).isEqualTo(1);
        assertThat(user.getGroups().contains(group)).isTrue();
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

}