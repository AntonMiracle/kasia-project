package com.kasia.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;

public class UserTest {

    @Test
    public void checkEqualsAndHashCode() {
        User u1 = new User(User.Role.USER, "email", "nick", "pass", ZoneId.systemDefault(), LocalDateTime.now());
        User u2 = new User(User.Role.USER, "email3", "nick3", "pass", ZoneId.systemDefault(), LocalDateTime.now());
        Budget b1 = new Budget("Name1", BigDecimal.TEN, Currency.getInstance("EUR"), LocalDateTime.now());
        Budget b2 = new Budget("Name2", BigDecimal.TEN, Currency.getInstance("USD"), LocalDateTime.now());

        EqualsVerifier.forClass(User.class)
                .usingGetClass()
                .withPrefabValues(Budget.class, b1, b2)
                .withPrefabValues(User.class, u1, u2)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}