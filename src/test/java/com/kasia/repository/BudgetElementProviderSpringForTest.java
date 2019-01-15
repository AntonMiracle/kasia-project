package com.kasia.repository;

import com.kasia.ModelTestData;
import com.kasia.model.BudgetElementProvider;
import com.kasia.model.repository.BudgetElementProviderRepository;
import com.kasia.model.repository.BudgetRepository;
import com.kasia.model.repository.ElementProviderRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetElementProviderSpringForTest {
    @Autowired
    private BudgetElementProviderRepository repository;
    @Autowired
    private ElementProviderRepository providerRepository;
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
        BudgetElementProvider budgetElementProvider = ModelTestData.getBudgetElementProvider1();
        assertThat(budgetElementProvider.getId() == 0).isTrue();

        saveForTest(budgetElementProvider);

        assertThat(budgetElementProvider.getId() > 0).isTrue();
    }

    private BudgetElementProvider saveForTest(BudgetElementProvider budgetElementProvider) {
        budgetElementProvider.getElementProviders().forEach(providerRepository::save);
        budgetRepository.save(budgetElementProvider.getBudget());
        repository.save(budgetElementProvider);
        return budgetElementProvider;
    }

    @Test
    public void getById() throws Exception {
        BudgetElementProvider budgetElementProvider = saveForTest(ModelTestData.getBudgetElementProvider1());
        long id = budgetElementProvider.getId();

        budgetElementProvider = repository.findById(id).get();

        assertThat(budgetElementProvider).isNotNull();
        assertThat(budgetElementProvider.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        BudgetElementProvider budgetElementProvider = saveForTest(ModelTestData.getBudgetElementProvider1());

        repository.delete(budgetElementProvider);

        assertThat(repository.findById(budgetElementProvider.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestData.getBudgetElementProvider1());
        saveForTest(ModelTestData.getBudgetElementProvider2());
        Set<BudgetElementProvider> budgetElementProviders = new HashSet<>();

        repository.findAll().forEach(budgetElementProviders::add);

        assertThat(budgetElementProviders.size() == 2).isTrue();
    }
}