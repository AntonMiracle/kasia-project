package com.kasia.service;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Article;
import com.kasia.model.Budget;
import org.junit.After;
import org.junit.Test;

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
        assertThat(budget.getCurrency()).isEqualTo(CURRENCY);
        assertThat(budget.getCreateOn()).isBefore(LocalDateTime.now());
        assertThat(budget.getArticles().size() == 0).isTrue();
        assertThat(budget.getOperations().size() == 0).isTrue();
        assertThat(budget.getEmployers().size() == 0).isTrue();
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

//    @Test
//    public void addArticle() throws Exception {
//        Budget budget = budgetService.create(NAME, BALANCE, CURRENCY);
//
//        assertThat(budget.getArticles().size() == 0).isTrue();
//        budget = budgetService.addArticle(budget, createArticle("name", Article.Type.INCOME, BigDecimal.TEN));
//
//        assertThat(budget.getArticles().size() == 1).isTrue();
//        assertThat(budgetService.getBudgetById(budget.getId()).getArticles().size() == 1).isTrue();
//    }
//
//    @Test
//    public void removeArticle() throws Exception {
//        Budget budget = budgetService.create(NAME, BALANCE, CURRENCY);
//        budget = budgetService.addArticle(budget, createArticle("name", Article.Type.INCOME, BigDecimal.TEN));
//
//        assertThat(budget.getArticles().size() == 1).isTrue();
//        for (Article art : budget.getArticles()) {
//            budgetService.removeArticle(budget, art);
//        }
//        assertThat(budget.getArticles().size() == 0).isTrue();
//        assertThat(budgetService.getBudgetById(budget.getId()).getArticles().size() == 0).isTrue();
//    }

    @Test
    public void getArticlesByType() {
        Budget budget = budgetService.create(NAME, BALANCE, CURRENCY);
        budget.getArticles().add(createArticle("name1", Article.Type.INCOME));
        budget.getArticles().add(createArticle("name11", Article.Type.CONSUMPTION));
        budget.getArticles().add(createArticle("name13", Article.Type.INCOME));
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

    @Test
    public void addOperation() {
        BigDecimal balance = BigDecimal.TEN;
        Budget budget = budgetService.create(NAME, balance, CURRENCY);

        ////// need User,Article,Employer services

    }

    @Test
    public void removeOperation() {

    }

    @Test
    public void getOperationsByArticlesType() {

    }

    private Article createArticle(String name, Article.Type type) {
        Article article = new Article(name, type);
        article.setDescription("description");
        return article;
    }
}