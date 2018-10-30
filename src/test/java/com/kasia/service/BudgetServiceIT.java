package com.kasia.service;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Article;
import com.kasia.model.Budget;
import com.kasia.repository.BudgetRepository;
import com.kasia.service.imp.BudgetServiceImp;
import com.oneandone.ejbcdiunit.EjbUnitRunner;
import com.oneandone.ejbcdiunit.persistence.TestPersistenceFactory;
import org.jglue.cdiunit.AdditionalClasses;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BudgetServiceIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private BudgetService budgetService;
    private final Currency CURRENCY = Currency.getInstance("EUR");
    private final String NAME = "name";
    private final String NAME_2 = "name22";
    private final BigDecimal BALANCE = BigDecimal.TEN;

    @After
    public void after() {
        for (Budget b : budgetService.getAllBudgets()) {
            budgetService.delete(b.getId());
        }
    }

    @Test
    public void create() throws Exception {
        Budget budget = budgetService.create(NAME, BALANCE, CURRENCY);

        assertThat(budget.getName()).isEqualTo(NAME);
        assertThat(budget.getBalance()).isEqualTo(BALANCE);
        assertThat(budget.getArticles().size() == 0).isTrue();
        assertThat(budget.getCurrency()).isEqualTo(CURRENCY);
        assertThat(budget.getCreateOn()).isBefore(LocalDateTime.now());
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
    public void addArticle() throws Exception {
        Budget budget = budgetService.create(NAME, BALANCE, CURRENCY);

        assertThat(budget.getArticles().size() == 0).isTrue();
        budget = budgetService.addArticle(budget, createArticle("name", Article.Type.INCOME, BigDecimal.TEN));

        assertThat(budget.getArticles().size() == 1).isTrue();
        assertThat(budgetService.getBudgetById(budget.getId()).getArticles().size() == 1).isTrue();
    }

    @Test
    public void removeArticle() throws Exception {
        Budget budget = budgetService.create(NAME, BALANCE, CURRENCY);
        budget = budgetService.addArticle(budget, createArticle("name", Article.Type.INCOME, BigDecimal.TEN));

        assertThat(budget.getArticles().size() == 1).isTrue();
        for (Article art : budget.getArticles()) {
            budgetService.removeArticle(budget, art);
        }
        assertThat(budget.getArticles().size() == 0).isTrue();
        assertThat(budgetService.getBudgetById(budget.getId()).getArticles().size() == 0).isTrue();
    }

    @Test
    public void getArticlesByType() {
        Budget budget = budgetService.create(NAME, BALANCE, CURRENCY);
        budget.getArticles().add(createArticle("description1", Article.Type.INCOME, BigDecimal.TEN));
        budget.getArticles().add(createArticle("description1", Article.Type.CONSUMPTION, BigDecimal.ONE));
        budget.getArticles().add(createArticle("description2", Article.Type.INCOME, BigDecimal.ZERO));
        budget = budgetService.update(budget);

        assertThat(budgetService.getArticlesByType(budget, Article.Type.INCOME).size() == 2);
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

    private Article createArticle(String description, Article.Type type, BigDecimal amount) {
        Article article = new Article();
        article.setDescription(description);
        article.setCreateOn(LocalDateTime.now());
        article.setType(type);
        article.setAmount(amount);
        return article;
    }
}