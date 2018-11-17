package com.kasia.model.service;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.*;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BudgetModelServiceIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private BudgetModelService budgetService;
    private final Currency CURRENCY = Currency.getInstance("EUR");
    private final String NAME = "name";
    private final String NAME_2 = "name22";
    private final BigDecimal BALANCE = BigDecimal.TEN;

    @After
    public void after() {
        for (Budget b : budgetService.getAllBudgets()) {
            budgetService.delete(b.getId());
        }
        for (Operation o : operationService.getAllOperations()) {
            operationService.delete(o.getId());
        }
        for (User u : userService.getAllUsers()) {
            userService.delete(u.getId());
        }
    }

    @Test
    public void create() throws Exception {
        Budget budget = budgetService.create(NAME, BALANCE, CURRENCY);

        assertThat(budget.getName()).isEqualTo(NAME);
        assertThat(budget.getBalance()).isEqualTo(BALANCE);
        assertThat(budget.getCurrency()).isEqualTo(CURRENCY);
        assertThat(budget.getCreateOn()).isBefore(LocalDateTime.now());
        assertThat(budget.getOperations().size() == 0).isTrue();
    }

    @Test
    public void delete() throws Exception {
        Budget budget = budgetService.create(NAME, BALANCE, CURRENCY);

        assertThat(budgetService.getBudgetById(budget.getId())).isNotNull();
        budgetService.delete(budget.getId());

        assertThat(budgetService.getBudgetById(budget.getId())).isNull();
    }


    @Test
    public void update() throws Exception {
        Budget budget = budgetService.create(NAME, BALANCE, CURRENCY);

        budget.setName(NAME_2);
        budget = budgetService.update(budget);

        assertThat(budget.getName()).isEqualTo(NAME_2);
    }

    @Test
    public void getBudgetById() throws Exception {
        Budget budget = budgetService.create(NAME, BALANCE, CURRENCY);

        assertThat(budgetService.getBudgetById(budget.getId())).isEqualTo(budget);
    }


    @Test
    public void getAllBudgets() {
        assertThat(budgetService.getAllBudgets()).isNotNull();
        assertThat(budgetService.getAllBudgets().size() == 0).isTrue();

        budgetService.create(NAME, BALANCE, CURRENCY);
        budgetService.create(NAME, BALANCE, CURRENCY);
        budgetService.create(NAME, BALANCE, CURRENCY);

        assertThat(budgetService.getAllBudgets().size() == 3).isTrue();
    }

    @Inject
    private OperationModelService operationService;
    @Inject
    private UserModelService userService;
    @Inject
    private EmployerModelService employerService;
    @Inject
    private ArticleModelService articleService;
    private final BigDecimal budgetBalance = BigDecimal.TEN;
    private final BigDecimal operationAmount = BigDecimal.ONE;

    private Operation newOperation(BigDecimal amount, Article.Type type) {
        Article article = articleService.create(NAME, type);
        User user = userService.create("email@gmail.com", "Password2", "NICK", ZoneId.systemDefault());
        Employer employer = employerService.create(NAME);
        return operationService.create(amount, article, user, employer);
    }

    @Test
    public void addIncomeOperation() {
        Budget budget = budgetService.create(NAME, budgetBalance, CURRENCY);
        Operation operation = newOperation(operationAmount, Article.Type.INCOME);

        assertThat(budget.getOperations().size() == 0).isTrue();
        budget = budgetService.addOperation(budget, operation);

        assertThat(budget.getBalance()).isEqualTo(budgetBalance.add(operationAmount));
        assertThat(budget.getOperations().size() == 1).isTrue();
    }


    @Test
    public void addConsumptionOperation() {
        Budget budget = budgetService.create(NAME, budgetBalance, CURRENCY);
        Operation operation = newOperation(operationAmount, Article.Type.CONSUMPTION);

        assertThat(budget.getOperations().size() == 0).isTrue();
        budget = budgetService.addOperation(budget, operation);

        assertThat(budget.getBalance()).isEqualTo(budgetBalance.subtract(operationAmount));
        assertThat(budget.getOperations().size() == 1).isTrue();
    }

    @Test
    public void removeIncomeOperation() {
        Budget budget = budgetService.create(NAME, budgetBalance, CURRENCY);
        Operation operation = newOperation(operationAmount, Article.Type.INCOME);
        budget = budgetService.addOperation(budget, operation);

        assertThat(budget.getOperations().size() == 1).isTrue();
        budget = budgetService.removeOperation(budget,operation);

        assertThat(budget.getOperations().size() == 0).isTrue();
        assertThat(budget.getBalance()).isEqualTo(budgetBalance);
    }

    @Test
    public void removeConsumptionOperation() {
        Budget budget = budgetService.create(NAME, budgetBalance, CURRENCY);
        Operation operation = newOperation(operationAmount, Article.Type.CONSUMPTION);
        budget = budgetService.addOperation(budget, operation);

        assertThat(budget.getOperations().size() == 1).isTrue();
        budget = budgetService.removeOperation(budget,operation);

        assertThat(budget.getOperations().size() == 0).isTrue();
        assertThat(budget.getBalance()).isEqualTo(budgetBalance);
    }

    @Test
    public void getOperationsByArticlesTypeIncome() {
        Budget budget = budgetService.create(NAME, budgetBalance, CURRENCY);
        assertThat(budgetService.getOperationsByArticlesType(budget,Article.Type.INCOME).size() == 0).isTrue();

        budgetService.addOperation(budget, newOperation(BigDecimal.TEN, Article.Type.INCOME));

        assertThat(budgetService.getOperationsByArticlesType(budget,Article.Type.INCOME).size() == 1).isTrue();
    }

    @Test
    public void getOperationsByArticlesTypeConsumption() {
        Budget budget = budgetService.create(NAME, budgetBalance, CURRENCY);
        assertThat(budgetService.getOperationsByArticlesType(budget,Article.Type.CONSUMPTION).size() == 0).isTrue();

        budgetService.addOperation(budget, newOperation(BigDecimal.TEN, Article.Type.CONSUMPTION));

        assertThat(budgetService.getOperationsByArticlesType(budget,Article.Type.CONSUMPTION).size() == 1).isTrue();
    }
}