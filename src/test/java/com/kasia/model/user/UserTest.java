package com.kasia.model.user;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {
    private User user;

    @Before
    public void before() {
        user = new User();
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
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(User.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}