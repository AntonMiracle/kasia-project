package com.kasia.repository;

import com.kasia.model.Article;
import com.kasia.model.Budget;
import com.kasia.repository.imp.BudgetRepositoryImp;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BudgetRepositoryTest extends RepositoryTestHelper {
    private BudgetRepository budgetRepository;
    private Budget budget;

    @Before
    public void before() {
        budgetRepository = new BudgetRepositoryImp(repositoryConnectionService.getEntityManager());
        budget = new Budget();
    }

    @Test
    public void getById() throws Exception {
        budget.setBalance(BigDecimal.TEN);
        budget.setCurrency(Currency.getInstance("EUR"));
        budget.setArticles(new HashSet<>());
        budget.setName("budgetName");
        budget.setCreateOn(LocalDateTime.now());

        budgetRepository.save(budget);

        assertThat(budgetRepository.getById(budget.getId())).isEqualTo(budget);
    }

    @Test
    public void delete() throws Exception {
        budget.setBalance(BigDecimal.TEN);
        budget.setCurrency(Currency.getInstance("EUR"));
        budget.setArticles(new HashSet<>());
        budget.setName("budgetName");
        budget.setCreateOn(LocalDateTime.now());

        budgetRepository.save(budget);
        long id = budget.getId();

        assertThat(budgetRepository.getById(id)).isNotNull();
        assertThat(budgetRepository.delete(budget)).isTrue();
        assertThat(budgetRepository.getById(id)).isNull();
    }

    @Test
    public void update() throws Exception {
        budget.setBalance(BigDecimal.TEN);
        budget.setCurrency(Currency.getInstance("EUR"));
        budget.setArticles(new HashSet<>());
        budget.setName("budgetName");
        budget.setCreateOn(LocalDateTime.now());

        budgetRepository.save(budget);
        long id = budget.getId();

        String newName = "newName";
        budget.setName(newName);

        Article article = new Article();
        article.setAmount(BigDecimal.TEN);
        article.setType(Article.Type.INCOME);
        article.setCreateOn(LocalDateTime.now());
        article.setDescription("descr");

        assertThat(article.getId() == 0).isTrue();
        budget.getArticles().add(article);
        budgetRepository.update(budget);

        budget = budgetRepository.getById(id);

        for (Article art : budget.getArticles()) {
            assertThat(art.getId() > 0).isTrue();
        }
        assertThat(budget.getName()).isEqualTo(newName);
    }

    @Test
    public void save() throws Exception {
        budget.setBalance(BigDecimal.TEN);
        budget.setCurrency(Currency.getInstance("EUR"));
        budget.setArticles(new HashSet<>());
        budget.setName("budgetName");
        budget.setCreateOn(LocalDateTime.now());

        budgetRepository.save(budget);

        assertThat(budget.getId() > 0).isTrue();
    }

    @Test
    public void getAll() {
        budget.setBalance(BigDecimal.TEN);
        budget.setCurrency(Currency.getInstance("EUR"));
        budget.setArticles(new HashSet<>());
        budget.setName("budgetName");
        budget.setCreateOn(LocalDateTime.now());

        Budget budget1 = new Budget();
        budget1.setBalance(BigDecimal.TEN);
        budget1.setCurrency(Currency.getInstance("EUR"));
        budget1.setArticles(new HashSet<>());
        budget1.setName("budgetName");
        budget1.setCreateOn(LocalDateTime.now());

        budgetRepository.save(budget);
        budgetRepository.save(budget1);

        assertThat(budgetRepository.getAll().size() == 2).isTrue();
    }
}