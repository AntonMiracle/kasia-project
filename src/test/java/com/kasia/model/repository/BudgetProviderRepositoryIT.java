package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.BudgetProvider;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetProviderRepositoryIT {
    @Autowired
    private BudgetElementProviderRepository repository;
    @Autowired
    private ProviderRepository providerRepository;
    @Autowired
    private BudgetRepository budgetRepository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
        budgetRepository.findAll().forEach(model -> budgetRepository.delete(model));
        providerRepository.findAll().forEach(model -> providerRepository.delete(model));
    }

    @Test
    public void save() {
        BudgetProvider budgetProvider = ModelTestData.getBudgetElementProvider1();
        assertThat(budgetProvider.getId() == 0).isTrue();

        saveForTest(budgetProvider);

        assertThat(budgetProvider.getId() > 0).isTrue();
    }

    private BudgetProvider saveForTest(BudgetProvider budgetProvider) {
        budgetProvider.getProviders().forEach(providerRepository::save);
        budgetRepository.save(budgetProvider.getBudget());
        repository.save(budgetProvider);
        return budgetProvider;
    }

    @Test
    public void getById() throws Exception {
        BudgetProvider budgetProvider = saveForTest(ModelTestData.getBudgetElementProvider1());
        long id = budgetProvider.getId();

        budgetProvider = repository.findById(id).get();

        assertThat(budgetProvider).isNotNull();
        assertThat(budgetProvider.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        BudgetProvider budgetProvider = saveForTest(ModelTestData.getBudgetElementProvider1());

        repository.delete(budgetProvider);

        assertThat(repository.findById(budgetProvider.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestData.getBudgetElementProvider1());
        saveForTest(ModelTestData.getBudgetElementProvider2());
        Set<BudgetProvider> budgetProviders = new HashSet<>();

        repository.findAll().forEach(budgetProviders::add);

        assertThat(budgetProviders.size() == 2).isTrue();
    }

    @Test
    public void findByBudgetId() {
        BudgetProvider expected = saveForTest(ModelTestData.getBudgetElementProvider1());

        Optional<BudgetProvider> actual = repository.findByBudgetId(expected.getBudget().getId());

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }
}