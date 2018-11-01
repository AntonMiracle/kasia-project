package com.kasia.repository;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Budget;
import org.junit.After;
import org.junit.Test;

import javax.ejb.EJB;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BudgetRepositoryIT extends ConfigurationEjbCdiContainerForIT {
    @EJB
    private BudgetRepository budgetRepository;
    private final BigDecimal BALANCE = BigDecimal.TEN;
    private final Currency CURRENCY_EUR = Currency.getInstance("EUR");
    private final String NAME = "Name";
    private final String NAME_2 = "Name2";
    private final LocalDateTime CREATE_ON = LocalDateTime.of(2018, 10, 10, 10, 10, 10);

    @After
    public void after() {
        for (Budget b : budgetRepository.getAll()) {
            budgetRepository.delete(b);
        }
    }

    @Test
    public void getById() throws Exception {
        Budget budget = new Budget(NAME, BALANCE, CURRENCY_EUR, CREATE_ON);
        budget.setOperations(new HashSet<>());
        long id = budgetRepository.save(budget).getId();

        assertThat(budgetRepository.getById(id)).isEqualTo(budget);
    }

    @Test
    public void getAll() throws Exception {
        Budget budget = new Budget(NAME, BALANCE, CURRENCY_EUR, CREATE_ON);
        Budget budget1 = new Budget(NAME, BALANCE, CURRENCY_EUR, CREATE_ON);
        budgetRepository.save(budget);
        budgetRepository.save(budget1);

        assertThat(budgetRepository.getAll().size() == 2).isTrue();
    }

    @Test
    public void delete() throws Exception {
        Budget budget = new Budget(NAME, BALANCE, CURRENCY_EUR, CREATE_ON);
        long id = budgetRepository.save(budget).getId();

        assertThat(budgetRepository.getById(id)).isNotNull();
        assertThat(budgetRepository.delete(budgetRepository.getById(id))).isTrue();
        assertThat(budgetRepository.getById(id)).isNull();
    }

    @Test
    public void save() throws Exception {
        Budget expected = new Budget(NAME, BALANCE, CURRENCY_EUR, CREATE_ON);
        expected.setOperations(new HashSet<>());

        long id = budgetRepository.save(expected).getId();
        Budget actual = budgetRepository.getById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        Budget budget = new Budget(NAME, BALANCE, CURRENCY_EUR, CREATE_ON);
        long id = budgetRepository.save(budget).getId();
        budget = budgetRepository.getById(id);

        budget.setName(NAME_2);
        budgetRepository.save(budget);

        budget = budgetRepository.getById(id);
        assertThat(budget.getName()).isEqualTo(NAME_2);
    }
}