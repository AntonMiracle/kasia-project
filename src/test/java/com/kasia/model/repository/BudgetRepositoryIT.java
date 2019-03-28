package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.Balance;
import com.kasia.model.Budget;
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
        Balance balance1 = ModelTestData.balance();
        balance1.setAmount(BigDecimal.ZERO);
        Balance balance2 = ModelTestData.balance();
        balance2.setAmount(BigDecimal.valueOf(0.0));
        Balance balance3 = ModelTestData.balance();
        balance3.setAmount(BigDecimal.valueOf(0.01));
        Balance balance4 = ModelTestData.balance();
        balance4.setAmount(BigDecimal.valueOf(-10.01));

        Budget budget1 = ModelTestData.budget();
        budget1.setBalance(balance1);
        Budget budget2 = ModelTestData.budget();
        budget2.setBalance(balance2);
        Budget budget3 = ModelTestData.budget();
        budget3.setBalance(balance3);
        Budget budget4 = ModelTestData.budget();
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
        Budget budget = ModelTestData.budget();
        assertThat(budget.getId() == 0).isTrue();

        saveForTest(budget);

        assertThat(budget.getId() > 0).isTrue();
    }

    private Budget saveForTest(Budget budget) {
        repository.save(budget);
        return budget;
    }

    @Test
    public void findById() {
        Budget budget = saveForTest(ModelTestData.budget());
        long id = budget.getId();

        budget = repository.findById(id).get();

        assertThat(budget).isNotNull();
        assertThat(budget.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        Budget budget = saveForTest(ModelTestData.budget());

        repository.delete(budget);

        assertThat(repository.findById(budget.getId()).isPresent()).isFalse();
    }

    @Test
    public void findAll() throws Exception {
        Budget budget1 = saveForTest(ModelTestData.budget());
        Budget budget2 = saveForTest(ModelTestData.budget());
        saveForTest(budget1);
        saveForTest(budget2);
        Set<Budget> budgets = new HashSet<>();

        repository.findAll().forEach(budgets::add);

        assertThat(budgets.size()).isEqualTo(2);
    }
}