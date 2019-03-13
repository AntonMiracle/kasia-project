package com.kasia;

import com.kasia.controller.dto.UserDTO;
import com.kasia.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

final public class ModelTestData {
    public static Balance getBalance1() {
        return new Balance(BigDecimal.valueOf(0.00), Currencies.EUR, getNow());
    }

    public static Balance getBalance2() {
        return new Balance(BigDecimal.valueOf(-0.01), Currencies.USD, getNow());
    }

    public static Price getPrice1() {
        return new Price(BigDecimal.ZERO, Currencies.EUR);
    }

    public static Price getPrice2() {
        return new Price(BigDecimal.TEN, Currencies.USD);
    }

    public static Budget getBudget1() {
        return new Budget("name1", getBalance1(), LocalDateTime.now());
    }

    public static Budget getBudget2() {
        return new Budget("name2", getBalance2(), LocalDateTime.now());
    }

    public static Element getElement1() {
        return new Element("name1", getPrice1(), ElementType.CONSUMPTION);
    }

    public static Element getElement2() {
        return new Element("name2", getPrice2(), ElementType.INCOME);
    }

    public static Provider getElementProvider1() {
        return new Provider("name1", "description1");
    }

    public static Provider getElementProvider2() {
        return new Provider("name2", "description2");
    }

    public static LocalDateTime getNow() {
        return LocalDateTime.now().withNano(0);
    }

    public static Operation getOperation1() {
        return new Operation(getUser1(), getElement1(), getElementProvider1(), getPrice1(), getNow(),"");
    }

    public static Operation getOperation2() {
        return new Operation(getUser2(), getElement2(), getElementProvider2(), getPrice2(), getNow(),"");
    }

    public static Currency getBudgetCurrency1() {
        return Currencies.EUR.getCurrency();
    }

    public static Currency getBudgetCurrency2() {
        return Currencies.PLN.getCurrency();
    }

    public static User getUser1() {
        return new User("email1@gmail.com", "Name1", "Password2", ZoneId.systemDefault(), getNow(), Role.USER, false, new Locale("en", "CA"));
    }

    public static User getUser2() {
        return new User("email2@gmail.com", "Name2", "Password3", ZoneId.systemDefault(), getNow(), Role.USER, false, new Locale("en", "SG"));
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

    public static BudgetProvider getBudgetElementProvider1() {
        Set<Provider> providers = new HashSet<>();
        providers.add(getElementProvider1());
        return new BudgetProvider(getBudget1(), providers);
    }

    public static BudgetProvider getBudgetElementProvider2() {
        Set<Provider> providers = new HashSet<>();
        providers.add(getElementProvider2());
        return new BudgetProvider(getBudget2(), providers);
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

    public static Locale getDefaultLocale() {
        return new Locale("pl", "PL");
    }

    public static UserDTO getUserDto1() {
        UserDTO dto = new UserDTO();
        User u = ModelTestData.getUser1();
        dto.setConfirm(u.getPassword());
        dto.setCountry(u.getLocale().getCountry());
        dto.setEmail(u.getEmail());
        dto.setLang(u.getLocale().getLanguage());
        dto.setName(u.getName());
        dto.setPassword(u.getPassword());
        dto.setZoneId(u.getZoneId().toString());
        return dto;
    }

}