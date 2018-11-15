package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

public class UserTest {

    @Test
    public void checkEqualsAndHashCode() {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);

        User u1 = new User("email@gmail.com", "Password2", "Nick", roles
                , new HashSet<>(), new HashSet<>(), new HashSet<>()
                , LocalDateTime.now().withNano(0), ZoneId.systemDefault());

        User u2 = new User("email2@gmail.com", "Password22", "Nick2", roles
                , new HashSet<>(), new HashSet<>(), new HashSet<>()
                , LocalDateTime.now().withNano(0), ZoneId.systemDefault());

        Budget b1 = new Budget("Name1", new HashSet<>(), BigDecimal.TEN, LocalDateTime.now(), Currency.getInstance("EUR"));
        Budget b2 = new Budget("Name2", new HashSet<>(), BigDecimal.TEN, LocalDateTime.now(), Currency.getInstance("USD"));

        EqualsVerifier.forClass(User.class)
                .usingGetClass()
                .withPrefabValues(Budget.class, b1, b2)
                .withPrefabValues(User.class, u1, u2)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}