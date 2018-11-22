package com.kasia.controller;

import com.kasia.model.*;
import com.kasia.model.service.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import static com.kasia.validation.ValidationService.Currency.*;

public class InitializedController {
    @Inject
    private UserService userService;
    @Inject
    private EmployerService employerService;
    @Inject
    private BudgetService budgetService;
    @Inject
    private OperationService operationService;
    @Inject
    private ArticleService articleService;

    @PostConstruct
    public void startUp() {
        dataForTesting();
    }

    @PreDestroy
    public void shutDown() {
    }

    private void dataForTesting() {
        if (userService.getByEmail("anton@gmail.com") != null) return;

        User user = userService.create("anton@gmail.com", "Password2", "Anton", ZoneId.systemDefault());

        Set<Employer> employers = new HashSet<>();
        Employer employer1 = employerService.create("Auchon");
        Employer employer2 = employerService.create("Dekatlon");
        Employer employer3 = employerService.create("MPK");
        employers.add(employer1);
        employers.add(employer2);
        employers.add(employer3);
        user.setEmployers(employers);

        Article article1 = articleService.create("Shoes", Article.Type.CONSUMPTION);
        Article article2 = articleService.create("Food", Article.Type.CONSUMPTION);
        Article article3 = articleService.create("ZPasd", Article.Type.INCOME);
        Article article4 = articleService.create("Prezent", Article.Type.INCOME);
        Set<Article> articles = new HashSet<>();
        articles.add(article1);
        articles.add(article2);
        articles.add(article3);
        articles.add(article4);
        user.setArticles(articles);

        Operation operation1 = operationService.create(BigDecimal.TEN, article1, user, employer1);
        Operation operation2 = operationService.create(BigDecimal.valueOf(100), article2, user, employer3);
        Operation operation3 = operationService.create(BigDecimal.valueOf(198.23), article2, user, employer1);
        Operation operation4 = operationService.create(BigDecimal.valueOf(999.99), article2, user, employer2);
        Operation operation5 = operationService.create(BigDecimal.valueOf(888.08), article3, user, employer3);
        Operation operation6 = operationService.create(BigDecimal.valueOf(100000), article4, user, employer2);
        Set<Operation> operations1 = new HashSet<>();
        Set<Operation> operations2 = new HashSet<>();
        Set<Operation> operations3 = new HashSet<>();
        operations1.add(operation1);
        operations2.add(operation2);
        operations2.add(operation3);
        operations3.add(operation4);
        operations3.add(operation5);
        operations3.add(operation6);

        Set<Budget> budgets = new HashSet<>();
        Budget budget1 = budgetService.create("BudgetName_1", BigDecimal.TEN, EUR.get());
        Budget budget2 = budgetService.create("BudgetName_22", BigDecimal.TEN, USD.get());
        Budget budget3 = budgetService.create("BudgetName.36", BigDecimal.TEN, RUB.get());
        Budget budget4 = budgetService.create("BudgetName-88", BigDecimal.TEN, PLN.get());
        budget1.setOperations(operations1);
        budget2.setOperations(operations2);
        budget3.setOperations(operations3);
        budgetService.update(budget1);
        budgetService.update(budget2);
        budgetService.update(budget3);
        budgetService.update(budget4);
        budgets.add(budget1);
        budgets.add(budget2);
        budgets.add(budget3);
        budgets.add(budget4);
        user.setBudgets(budgets);

        userService.update(user);
    }
}
