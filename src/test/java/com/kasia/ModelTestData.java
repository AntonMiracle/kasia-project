package com.kasia;

import com.kasia.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

final public class ModelTestData {
    public static Balance balance() {
        return new Balance(BigDecimal.valueOf(0), Currencies.EUR.name(), now());
    }

    public static Budget budget() {
        return new Budget("budgetName", balance(), now());
    }

    public static Element element() {
        return new Element("elementName", price(0));
    }

    public static BigDecimal price(double price) {
        return new BigDecimal(price);
    }

    public static Place provider() {
        return new Place("providerName", "provider description");
    }

    public static LocalDateTime now() {
        return LocalDateTime.now().withNano(0);
    }

    public static Operation operation() {
        return new Operation(user(), element(), provider(), price(10), now(), "operation description", OperationType.CONSUMPTION);
    }

    public static User user() {
        return new User("email@gmail.com", "Password2", Role.USER, now(), true, ZoneId.systemDefault(), Locale.getDefault());
    }

    public static BudgetElement budgetElement() {
        Set<Element> elements = new HashSet<>();
        elements.add(element());
        return new BudgetElement(budget(), elements);
    }

    public static BudgetPlace budgetProvider() {
        Set<Place> places = new HashSet<>();
        places.add(provider());
        return new BudgetPlace(budget(), places);
    }

    public static BudgetOperation budgetOperation() {
        Set<Operation> operations = new HashSet<>();
        operations.add(operation());
        return new BudgetOperation(budget(), operations);
    }

    public static UserBudget userBudget() {
        Set<Budget> budgets = new HashSet<>();
        budgets.add(budget());
        return new UserBudget(user(), budgets);
    }

    public static UserConnectBudget userConnectBudget() {
        Set<Budget> budgets = new HashSet<>();
        budgets.add(budget());
        return new UserConnectBudget(user(), budgets);
    }

}