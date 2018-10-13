package com.kasia.repository;

import com.kasia.model.Article;
import com.kasia.model.Budget;
import com.kasia.model.Economy;
import com.kasia.repository.imp.EconomyRepositoryImp;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EconomyRepositoryTest extends RepositoryTestHelper {
    private EconomyRepository economyRepository;
    private Economy economy;

    @Before
    public void before() {
        economyRepository = new EconomyRepositoryImp(repositoryConnectionService.getEntityManager());
        economy = new Economy();
    }

    @Test
    public void getById() throws Exception {
        economy.setBudgets(new HashSet<>());
        economy.setName("Economy");
        economy.setCreateOn(LocalDateTime.now());

        economyRepository.save(economy);

        assertThat(economyRepository.getById(economy.getId())).isEqualTo(economy);
    }

    @Test
    public void delete() throws Exception {
        economy.setBudgets(new HashSet<>());
        economy.setName("Economy");
        economy.setCreateOn(LocalDateTime.now());

        economyRepository.save(economy);
        long id = economy.getId();

        assertThat(economyRepository.getById(id)).isNotNull();
        assertThat(economyRepository.delete(economy)).isTrue();
        assertThat(economyRepository.getById(id)).isNull();
    }

    @Test
    public void update() throws Exception {
        economy.setBudgets(new HashSet<>());
        economy.setName("Economy");
        economy.setCreateOn(LocalDateTime.now());

        economyRepository.save(economy);
        long id = economy.getId();

        String newName = "name";
        economy.setName(newName);

        Article article = new Article();
        article.setAmount(BigDecimal.TEN);
        article.setType(Article.Type.INCOME);
        article.setCreateOn(LocalDateTime.now());
        article.setDescription("descr");

        Budget budget = new Budget();
        budget.setBalance(BigDecimal.TEN);
        budget.setCurrency(Currency.getInstance("EUR"));
        budget.setArticles(new HashSet<>());
        budget.setName("budgetName");
        budget.setCreateOn(LocalDateTime.now());
        budget.getArticles().add(article);

        economy.getBudgets().add(budget);

        economyRepository.update(economy);

        for (Budget b : economy.getBudgets()) {
            assertThat(b.getId() > 0).isTrue();
        }
        assertThat(economyRepository.getById(id).getName()).isEqualTo(newName);
    }

    @Test
    public void save() throws Exception {
        economy.setBudgets(new HashSet<>());
        economy.setName("Economy");
        economy.setCreateOn(LocalDateTime.now());

        economyRepository.save(economy);

    }

    @Test
    public void getAll() {
        economy.setBudgets(new HashSet<>());
        economy.setName("Economy");
        economy.setCreateOn(LocalDateTime.now());

        Economy economy1 = new Economy();
        economy1.setBudgets(new HashSet<>());
        economy1.setName("Economy");
        economy1.setCreateOn(LocalDateTime.now());

        economyRepository.save(economy);
        economyRepository.save(economy1);
        assertThat(economyRepository.getAll().size() == 2).isTrue();
    }

}