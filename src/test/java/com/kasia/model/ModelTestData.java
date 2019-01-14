package com.kasia.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

final public class ModelTestData {
    public static Price getPrice1() {
        return new Price(BigDecimal.ZERO, Currency.getInstance("USD"));
    }

    public static Price getPrice2() {
        return new Price(BigDecimal.TEN, Currency.getInstance("USD"));
    }

    public static Budget getBudget1() {
        return new Budget("name1", getPrice1(), LocalDateTime.now());
    }

    public static Budget getBudget2() {
        return new Budget("name2", getPrice2(), LocalDateTime.now());
    }

    public static Element getElement1() {
        return new Element("name1", getPrice1(), ElementType.CONSUMPTION);
    }

    public static Element getElement2() {
        return new Element("name1", getPrice2(), ElementType.INCOME);
    }

    public static ElementProvider getElementProvider1() {
        return new ElementProvider("name1", "description1");
    }

    public static ElementProvider getElementProvider2() {
        return new ElementProvider("name2", "description2");
    }

    public static LocalDateTime getNow() {
        return LocalDateTime.now().withNano(0);
    }

    public static Operation getOperation1() {
        return new Operation(getUser1(), getElement1(), getElementProvider1(), getPrice1(), getNow());
    }

    public static Operation getOperation2() {
        return new Operation(getUser2(), getElement2(), getElementProvider2(), getPrice2(), getNow());
    }

    public static Currency getBudgetCurrency1() {
        return Currencies.EUR.getCurrency();
    }

    public static Currency getBudgetCurrency2() {
        return Currencies.PLN.getCurrency();
    }

    public static User getUser1() {
        return new User("email2@gmail.com", "Name1", "Password2", ZoneId.systemDefault(), getNow());
    }

    public static User getUser2() {
        return new User("email3@gmail.com", "Name2", "Password3", ZoneId.systemDefault(), getNow().minusDays(2));
    }

    public static BudgetElement getBudgetElement1() {
        Set<Element> elements = new HashSet<>();
        elements.add(getElement1());
        return new BudgetElement(getBudget1(), elements);
    }

    public static BudgetElement getBudgetElement2() {
        Set<Element> elements = new HashSet<>();
        elements.add(getElement2());
        return new BudgetElement(getBudget2(), elements);
    }

    public static BudgetElementProvider getBudgetElementProvider1() {
        Set<ElementProvider> providers = new HashSet<>();
        providers.add(getElementProvider1());
        return new BudgetElementProvider(getBudget1(), providers);
    }

    public static BudgetElementProvider getBudgetElementProvider2() {
        Set<ElementProvider> providers = new HashSet<>();
        providers.add(getElementProvider2());
        return new BudgetElementProvider(getBudget2(), providers);
    }

    public static BudgetOperation getBudgetOperation1() {
        Set<Operation> operations = new HashSet<>();
        operations.add(getOperation1());
        return new BudgetOperation(getBudget1(), operations);
    }

    public static BudgetOperation getBudgetOperation2() {
        Set<Operation> operations = new HashSet<>();
        operations.add(getOperation2());
        return new BudgetOperation(getBudget1(), operations);
    }

    public static UserBudget getUserBudget1() {
        Set<Budget> budgets = new HashSet<>();
        budgets.add(getBudget1());
        return new UserBudget(getUser1(), budgets);
    }

    public static UserBudget getUserBudget2() {
        Set<Budget> budgets = new HashSet<>();
        budgets.add(getBudget2());
        return new UserBudget(getUser2(), budgets);
    }

    public static UserConnectBudget getUserConnectBudget1() {
        Set<Budget> budgets = new HashSet<>();
        budgets.add(getBudget1());
        return new UserConnectBudget(getUser1(), budgets);
    }

    public static UserConnectBudget getUserConnectBudget2() {
        Set<Budget> budgets = new HashSet<>();
        budgets.add(getBudget2());
        return new UserConnectBudget(getUser2(), budgets);
    }
}