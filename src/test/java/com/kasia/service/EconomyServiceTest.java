package com.kasia.service;

import com.kasia.model.Budget;
import com.kasia.model.Economy;
import com.kasia.repository.RepositoryTestHelper;
import com.kasia.repository.imp.EconomyRepositoryImp;
import com.kasia.service.imp.EconomyServiceImp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EconomyServiceTest extends RepositoryTestHelper {
    private EconomyService economyService;
    private Economy economy;
    private final String NAME = "name";
    private final LocalDateTime NOW = LocalDateTime.now();

    @Before
    public void before() {
        economyService = new EconomyServiceImp(new EconomyRepositoryImp(repositoryConnectionService.getEntityManager()));
        economy = new Economy();
    }

    @After
    public void after() {
        for (Economy e : economyService.getAll()) {
            economyService.delete(e.getId());
        }
    }

    @Test
    public void create() throws Exception {
        economy = economyService.create(NAME);
        long id = economy.getId();
        assertThat(economyService.getEconomyById(id).getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        economy = economyService.create(NAME);
        long id = economy.getId();
        economyService.delete(id);
        assertThat(economyService.getEconomyById(id)).isNull();

    }

    @Test
    public void update() throws Exception {
        String newName = "newName";
        economy = economyService.create(NAME);
        long id = economy.getId();

        economy.setName(newName);
        economyService.update(economy);
        assertThat(economyService.getEconomyById(id).getName()).isEqualTo(newName);
    }

    @Test
    public void getEconomyById() throws Exception {
        economy = economyService.create(NAME);
        long id = economy.getId();
        assertThat(economyService.getEconomyById(id)).isEqualTo(economy);
    }

    @Test
    public void addBudget() throws Exception {
        economy = economyService.create(NAME);
        long id = economy.getId();

        economyService.addBudget(economy, createBudget(BigDecimal.TEN, Currency.getInstance("EUR")));
        assertThat(economy.getBudgets().size() == 1).isTrue();
        assertThat(economyService.getEconomyById(id).getBudgets().size() == 1).isTrue();
    }

    @Test
    public void removeBudget() throws Exception {
        economy = economyService.create(NAME);
        long id = economy.getId();
        economyService.addBudget(economy, createBudget(BigDecimal.TEN, Currency.getInstance("EUR")));

        for (Budget budget : economy.getBudgets()) {
            economyService.removeBudget(economy, budget);
        }
        assertThat(economyService.getEconomyById(id).getBudgets().size() == 0).isTrue();
    }

    @Test
    public void getBalance() throws Exception {
        Currency eur = Currency.getInstance("EUR");
        Currency usd = Currency.getInstance("USD");
        Currency rub = Currency.getInstance("RUB");
        economy = economyService.create(NAME);
        economyService.addBudget(economy, createBudget(BigDecimal.TEN, eur));
        economyService.addBudget(economy, createBudget(BigDecimal.ONE, eur));
        economyService.addBudget(economy, createBudget(BigDecimal.ZERO, eur));
        economyService.addBudget(economy, createBudget(BigDecimal.valueOf(100), usd));
        economyService.addBudget(economy, createBudget(BigDecimal.valueOf(9), usd));
        economyService.addBudget(economy, createBudget(BigDecimal.valueOf(0), rub));

        Map<Currency, BigDecimal> balance = economyService.getBalance(economy);
        assertThat(balance.keySet().size() == 3).isTrue();
        assertThat(balance.get(eur)).isEqualTo(BigDecimal.valueOf(11));
        assertThat(balance.get(usd)).isEqualTo(BigDecimal.valueOf(109));
        assertThat(balance.get(rub)).isEqualTo(BigDecimal.valueOf(0));
    }

    private Budget createBudget(BigDecimal balance, Currency currency) {
        Budget budget = new Budget();
        budget.setName("name");
        budget.setBalance(balance);
        budget.setArticles(new HashSet<>());
        budget.setCreateOn(LocalDateTime.now());
        budget.setCurrency(currency);
        return budget;
    }

}