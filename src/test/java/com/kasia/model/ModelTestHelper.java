package com.kasia.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Locale;

final public class ModelTestHelper {
    static Price getPrice1() {
        return new Price(BigDecimal.ZERO, Currency.getInstance("USD"));
    }

    static Price getPrice2() {
        return new Price(BigDecimal.TEN, Currency.getInstance("USD"));
    }

    static Budget getBudget1() {
        return new Budget("name1", getPrice1(), LocalDateTime.now());
    }

    static Budget getBudget2() {
        return new Budget("name2", getPrice2(), LocalDateTime.now());
    }

    static Element getElement1() {
        return new Element("name1", getPrice1(), ElementType.CONSUMPTION);
    }

    static Element getElement2() {
        return new Element("name1", getPrice2(), ElementType.INCOME);
    }

    static ElementProvider getElementProvider1() {
        return new ElementProvider("name1", "description1");
    }

    static ElementProvider getElementProvider2() {
        return new ElementProvider("name2", "description2");
    }

    static LocalDateTime getNow() {
        return LocalDateTime.now().withNano(0);
    }

    static Operation getOperation1() {
        return new Operation(getElement1(), getElementProvider1(), getPrice1(), getNow());
    }

    static Operation getOperation2() {
        return new Operation(getElement2(), getElementProvider2(), getPrice2(), getNow());
    }

    static Currency getBudgetCurrency1() {
        return Currencies.EUR.getCurrency();
    }

    static Currency getBudgetCurrency2() {
        return Currencies.PLN.getCurrency();
    }

    static User getUser1() {
        return new User("email2@gmail.com", "Name1", "Password2", ZoneId.systemDefault(), Locale.getDefault(), getNow());
    }

    static User getUser2() {
        return new User("email3@gmail.com", "Name2", "Password3", ZoneId.systemDefault(), Locale.getDefault(), getNow().minusDays(2));
    }
}