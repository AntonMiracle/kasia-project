package com.kasia.repository;

import com.kasia.model.Budget;
import com.kasia.model.ModelTestData;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetRepositoryIT extends ConfigurationRepositoryIT {
    @Autowired
    private BudgetRepository repository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
    }

    @Test
    public void save() throws Exception {
        Budget budget = ModelTestData.getBudget1();
        assertThat(budget.getId() == 0).isTrue();

        saveForTest(budget);

        assertThat(budget.getId() > 0).isTrue();
    }

    private Budget saveForTest(Budget budget) {
        repository.save(budget);
        return budget;
    }

    @Test
    public void getById() {
        Budget budget = saveForTest(ModelTestData.getBudget1());
        long id = budget.getId();

        budget = repository.findById(id).get();

        assertThat(budget).isNotNull();
        assertThat(budget.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        Budget budget = saveForTest(ModelTestData.getBudget1());

        repository.delete(budget);

        assertThat(repository.findById(budget.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestData.getBudget1());
        saveForTest(ModelTestData.getBudget2());
        Set<Budget> budgets = new HashSet<>();

        repository.findAll().forEach(budgets::add);

        assertThat(budgets.size()).isEqualTo(2);
    }
}