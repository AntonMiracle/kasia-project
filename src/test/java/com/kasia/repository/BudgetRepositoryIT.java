package com.kasia.repository;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Budget;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BudgetRepositoryIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private BudgetRepository repository;
    private final BigDecimal BALANCE = BigDecimal.TEN;
    private final Currency CURRENCY_EUR = Currency.getInstance("EUR");
    private final String NAME = "Name";
    private final String NAME_2 = "Name2";
    private final LocalDateTime CREATE_ON = LocalDateTime.of(2018, 10, 10, 10, 10, 10);

    @After
    public void after() {
        for (Budget b : repository.getAll()) {
            repository.delete(b);
        }
    }

    @Test
    public void getById() throws Exception {
        Budget budget = new Budget(NAME,new HashSet<>(), BALANCE,  CREATE_ON, CURRENCY_EUR);
        budget.setOperations(new HashSet<>());
        long id = repository.save(budget).getId();

        assertThat(repository.getById(id)).isEqualTo(budget);
    }

    @Test
    public void getAll() throws Exception {
        Budget budget = new Budget(NAME,new HashSet<>(), BALANCE,  CREATE_ON, CURRENCY_EUR);
        Budget budget1 = new Budget(NAME,new HashSet<>(), BALANCE,  CREATE_ON, CURRENCY_EUR);
        repository.save(budget);
        repository.save(budget1);

        assertThat(repository.getAll().size() == 2).isTrue();
    }

    @Test
    public void delete() throws Exception {
        Budget budget = new Budget(NAME,new HashSet<>(), BALANCE,  CREATE_ON, CURRENCY_EUR);
        long id = repository.save(budget).getId();

        assertThat(repository.getById(id)).isNotNull();
        assertThat(repository.delete(repository.getById(id))).isTrue();
        assertThat(repository.getById(id)).isNull();
    }

    @Test
    public void save() throws Exception {
        Budget expected = new Budget(NAME,new HashSet<>(), BALANCE,  CREATE_ON, CURRENCY_EUR);
        expected.setOperations(new HashSet<>());

        long id = repository.save(expected).getId();
        Budget actual = repository.getById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        Budget budget = new Budget(NAME,new HashSet<>(), BALANCE,  CREATE_ON, CURRENCY_EUR);
        long id = repository.save(budget).getId();
        budget = repository.getById(id);

        budget.setName(NAME_2);
        repository.save(budget);

        budget = repository.getById(id);
        assertThat(budget.getName()).isEqualTo(NAME_2);
    }
}