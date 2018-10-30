package com.kasia.service;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Budget;
import com.kasia.model.Economy;
import com.kasia.repository.EconomyRepository;
import com.kasia.service.imp.EconomyServiceImp;
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
import java.util.HashSet;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EconomyServiceIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private EconomyService economyService;
    private final String NAME = "name";
    private final String NAME_2 = "name2";

    @After
    public void after() {
        for (Economy e : economyService.getAll()) {
            economyService.delete(e.getId());
        }
    }

    @Test
    public void create() throws Exception {
        Economy economy = economyService.create(NAME);

        assertThat(economy.getId() > 0).isTrue();
        assertThat(economy.getName()).isEqualTo(NAME);
        assertThat(economy.getBudgets()).isNotNull();
        assertThat(economy.getBudgets().size() == 0).isTrue();
    }

    @Test
    public void delete() throws Exception {
        Economy economy = economyService.create(NAME);

        assertThat(economyService.getEconomyById(economy.getId())).isNotNull();
        economyService.delete(economy.getId());

        assertThat(economyService.getEconomyById(economy.getId())).isNull();
    }

    @Test
    public void update() throws Exception {
        Economy economy = economyService.create(NAME);

        economy.setName(NAME_2);
        economy = economyService.update(economy);

        assertThat(economy.getName()).isEqualTo(NAME_2);
    }

    @Test
    public void getEconomyById() throws Exception {
        Economy economy = economyService.create(NAME);

        assertThat(economyService.getEconomyById(economy.getId())).isEqualTo(economy);
    }

    @Test
    public void addBudget() throws Exception {
        Economy economy = economyService.create(NAME);
        assertThat(economy.getBudgets().size() == 0).isTrue();

        economy = economyService.addBudget(economy, createBudget(BigDecimal.TEN, Currency.getInstance("EUR")));

        assertThat(economy.getBudgets().size() == 1).isTrue();
        assertThat(economyService.getEconomyById(economy.getId()).getBudgets().size() == 1).isTrue();
    }

    @Test
    public void removeBudget() throws Exception {
        Economy economy = economyService.create(NAME);
        economyService.addBudget(economy, createBudget(BigDecimal.TEN, Currency.getInstance("EUR")));
        assertThat(economy.getBudgets().size() == 1).isTrue();

        for (Budget budget : economy.getBudgets()) {
            economyService.removeBudget(economy, budget);
        }
        assertThat(economyService.getEconomyById(economy.getId()).getBudgets().size() == 0).isTrue();
    }

    @Test
    public void getBalance() throws Exception {
        Currency eur = Currency.getInstance("EUR");
        Currency usd = Currency.getInstance("USD");
        Currency rub = Currency.getInstance("RUB");
        Economy economy = economyService.create(NAME);
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