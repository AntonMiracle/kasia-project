package com.kasia.repository;

import com.kasia.model.BudgetElement;
import com.kasia.model.ModelTestData;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetElementRepositoryIT extends ConfigurationRepositoryIT {
    @Autowired
    private BudgetElementRepository repository;
    @Autowired
    private ElementRepository elementRepository;
    @Autowired
    private BudgetRepository budgetRepository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
        budgetRepository.findAll().forEach(model -> budgetRepository.delete(model));
        elementRepository.findAll().forEach(model -> elementRepository.delete(model));
    }

    @Test
    public void save() {
        BudgetElement budgetElement = ModelTestData.getBudgetElement1();
        assertThat(budgetElement.getId() == 0).isTrue();

        saveForTest(budgetElement);

        assertThat(budgetElement.getId() > 0).isTrue();
    }

    private BudgetElement saveForTest(BudgetElement budgetElement) {
        budgetElement.getElements().forEach(elementRepository::save);
        budgetRepository.save(budgetElement.getBudget());
        repository.save(budgetElement);
        return budgetElement;
    }

    @Test
    public void getById() throws Exception {
        BudgetElement budgetElement = saveForTest(ModelTestData.getBudgetElement1());
        long id = budgetElement.getId();

        budgetElement = repository.findById(id).get();

        assertThat(budgetElement).isNotNull();
        assertThat(budgetElement.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        BudgetElement budgetElement = saveForTest(ModelTestData.getBudgetElement1());

        repository.delete(budgetElement);

        assertThat(repository.findById(budgetElement.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestData.getBudgetElement1());
        saveForTest(ModelTestData.getBudgetElement2());
        Set<BudgetElement> budgetElements = new HashSet<>();

        repository.findAll().forEach(budgetElements::add);

        assertThat(budgetElements.size() == 2).isTrue();
    }

}