package com.kasia.repository;

import com.kasia.ModelTestData;
import com.kasia.model.Balance;
import com.kasia.model.Budget;
import com.kasia.model.repository.BudgetRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetRepositoryIT {
    @Autowired
    private BudgetRepository repository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
    }

    @Test
    public void correctBigDecimalConvert() {
        Balance balance1 = ModelTestData.getBalance1();
        balance1.setAmount(BigDecimal.ZERO);
        Balance balance2 = ModelTestData.getBalance1();
        balance2.setAmount(BigDecimal.valueOf(0.0));
        Balance balance3 = ModelTestData.getBalance1();
        balance3.setAmount(BigDecimal.valueOf(0.01));
        Balance balance4 = ModelTestData.getBalance1();
        balance4.setAmount(BigDecimal.valueOf(-10.01));

        Budget budget1 = ModelTestData.getBudget1();
        budget1.setBalance(balance1);
        Budget budget2 = ModelTestData.getBudget1();
        budget2.setBalance(balance2);
        Budget budget3 = ModelTestData.getBudget1();
        budget3.setBalance(balance3);
        Budget budget4 = ModelTestData.getBudget1();
        budget4.setBalance(balance4);

        repository.save(budget1);
        repository.save(budget2);
        repository.save(budget3);
        repository.save(budget4);

        assertThat(repository.findById(budget1.getId()).get().getBalance()).isEqualTo(balance1);
        assertThat(repository.findById(budget2.getId()).get().getBalance()).isEqualTo(balance2);
        assertThat(repository.findById(budget3.getId()).get().getBalance()).isEqualTo(balance3);
        assertThat(repository.findById(budget4.getId()).get().getBalance()).isEqualTo(balance4);

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