package com.kasia.service;

import com.kasia.model.Article;
import com.kasia.model.Budget;
import com.kasia.repository.RepositoryTestHelper;
import com.kasia.repository.imp.BudgetRepositoryImp;
import com.kasia.service.imp.BudgetServiceImp;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BudgetServiceTest extends RepositoryTestHelper {
    private BudgetService budgetService;
    private Budget budget;
    private final Currency CURRENCY = Currency.getInstance("EUR");
    private final String NAME = "name";
    private final BigDecimal BALANCE = BigDecimal.ONE;

    @Before
    public void before() {
        budgetService = new BudgetServiceImp(new BudgetRepositoryImp(repositoryConnectionService.getEntityManager()));
    }

    @Test
    public void create() throws Exception {
        budget = budgetService.create(NAME, BALANCE, CURRENCY);
        budget = budgetService.getBudgetById(budget.getId());
        assertThat(budget.getName()).isEqualTo(NAME);
        assertThat(budget.getBalance()).isEqualTo(BALANCE);
        assertThat(budget.getArticles().size() == 0).isTrue();
        assertThat(budget.getCurrency()).isEqualTo(CURRENCY);
        assertThat(budget.getCreateOn()).isBefore(LocalDateTime.now());
    }

    @Test
    public void delete() throws Exception {
        budget = budgetService.create(NAME, BALANCE, CURRENCY);
        long id = budget.getId();
        assertThat(budgetService.getBudgetById(id)).isNotNull();
        budgetService.delete(id);
        assertThat(budgetService.getBudgetById(id)).isNull();
    }

    @Test
    public void update() throws Exception {
        budget = budgetService.create(NAME, BALANCE, CURRENCY);
        budget.setName("newName");
        long id = budget.getId();
        assertThat(budgetService.getBudgetById(id).getName()).isEqualTo("newName");
    }

    @Test
    public void getBudgetById() throws Exception {
        budget = budgetService.create(NAME, BALANCE, CURRENCY);
        long id = budget.getId();
        assertThat(budgetService.getBudgetById(id).getId()).isEqualTo(id);
    }

    @Test
    public void addArticle() throws Exception {
        budget = budgetService.create(NAME, BALANCE, CURRENCY);
        budgetService.addArticle(budget, createArticle("name", Article.Type.INCOME, BigDecimal.TEN));
        assertThat(budgetService.getBudgetById(budget.getId()).getArticles().size() == 1).isTrue();
    }

    @Test
    public void removeArticle() throws Exception {
        budget = budgetService.create(NAME, BALANCE, CURRENCY);
        budgetService.addArticle(budget, createArticle("name", Article.Type.INCOME, BigDecimal.TEN));
        for (Article art : budget.getArticles()) {
            budgetService.removeArticle(budget, art);
        }
        assertThat(budgetService.getBudgetById(budget.getId()).getArticles().size() == 0).isTrue();
    }

    @Test
    public void getArticlesByType() {
        budget = budgetService.create(NAME, BALANCE, CURRENCY);
        budget.getArticles().add(createArticle("description1", Article.Type.INCOME, BigDecimal.TEN));
        budget.getArticles().add(createArticle("description1", Article.Type.CONSUMPTION, BigDecimal.ONE));
        budget.getArticles().add(createArticle("description2", Article.Type.INCOME, BigDecimal.ZERO));
        budgetService.update(budget);
        budget = budgetService.getBudgetById(budget.getId());
        assertThat(budgetService.getArticlesByType(budget, Article.Type.INCOME).size() == 2);
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