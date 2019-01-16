package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.IdRuntimeException;
import com.kasia.model.Budget;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetServiceIT {
    @Autowired
    private BudgetService budgetService;

    @After
    public void after() {
        for (Budget budget : budgetService.findAll()) budgetService.delete(budget);
    }

    @Test
    public void create() throws Exception {
        Budget budget = ModelTestData.getBudget1();
        Budget createdBudget = budgetService.create(budget.getName(), budget.getBalance());

        assertThat(createdBudget.getId() == 0).isTrue();
        assertThat(createdBudget.getName()).isEqualTo(budget.getName());
        assertThat(createdBudget.getBalance()).isEqualTo(budget.getBalance());
        assertThat(createdBudget.getCreateOn()).isNotNull();
        assertThat(createdBudget.getCreateOn().compareTo(LocalDateTime.now().plusSeconds(2)) < 0).isTrue();
    }

    @Test
    public void saveNew() {
        Budget budget = ModelTestData.getBudget1();
        assertThat(budget.getId() == 0).isTrue();

        budgetService.save(budget);

        assertThat(budget.getId() > 0).isTrue();
        assertThat(budgetService.findById(budget.getId())).isNotNull();
    }

    @Test
    public void saveUpdate() {
        Budget budget = ModelTestData.getBudget1();
        budgetService.save(budget);
        String newName = "newName";
        long id = budget.getId();

        budget.setName(newName);
        budgetService.save(budget);

        assertThat(budgetService.findById(id).getName()).isEqualTo(newName);
    }

    @Test
    public void findAll() {
        assertThat(budgetService.findAll().size() == 0).isTrue();
        budgetService.save(ModelTestData.getBudget1());
        budgetService.save(ModelTestData.getBudget2());

        assertThat(budgetService.findAll().size() == 2).isTrue();
    }

    @Test
    public void delete() {
        Budget budget = ModelTestData.getBudget1();
        budgetService.save(budget);

        budgetService.delete(budget);

        assertThat(budgetService.findById(budget.getId())).isNull();
    }

    @Test(expected = IdRuntimeException.class)
    public void whenDeleteWithZeroIdThenException() {
        Budget budget = ModelTestData.getBudget1();
        budget.setId(0);
        budgetService.delete(budget);
    }

    @Test(expected = IdRuntimeException.class)
    public void whenDeleteWithNegativeIdThenException() {
        Budget budget = ModelTestData.getBudget1();
        budget.setId(-1);
        budgetService.delete(budget);
    }

    @Test
    public void findById() {
        Budget budget = ModelTestData.getBudget1();

        budgetService.save(budget);

        assertThat(budgetService.findById(budget.getId())).isEqualTo(budget);
        assertThat(budgetService.findById(budget.getId() + 1)).isNull();
    }

    @Test(expected = IdRuntimeException.class)
    public void whenFindByIdWithZeroIdThenException() {
        assertThat(budgetService.findById(0)).isNotNull();
    }

    @Test(expected = IdRuntimeException.class)
    public void whenFindByIdWithNegativeIdThenException() {
        assertThat(budgetService.findById(-1)).isNotNull();
    }

}