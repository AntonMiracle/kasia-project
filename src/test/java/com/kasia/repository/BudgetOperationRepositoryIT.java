package com.kasia.repository;

import com.kasia.model.BudgetOperation;
import com.kasia.model.ModelTestData;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetOperationRepositoryIT extends ConfigurationRepositoryIT {
    @Autowired
    private BudgetOperationRepository repository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private ElementRepository elementRepository;
    @Autowired
    private ElementProviderRepository providerRepository;
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
        budgetRepository.findAll().forEach(model -> budgetRepository.delete(model));
        operationRepository.findAll().forEach(model -> operationRepository.delete(model));
        elementRepository.findAll().forEach(model -> elementRepository.delete(model));
        providerRepository.findAll().forEach(model -> providerRepository.delete(model));
    }

    @Test
    public void save() {
        BudgetOperation budgetOperation = ModelTestData.getBudgetOperation1();
        assertThat(budgetOperation.getId() == 0).isTrue();

        saveForTest(budgetOperation);

        assertThat(budgetOperation.getId() > 0).isTrue();
    }

    private BudgetOperation saveForTest(BudgetOperation budgetOperation) {
        budgetOperation.getOperations().forEach(operation ->
        {
            elementRepository.save(operation.getElement());
            providerRepository.save(operation.getElementProvider());
            userRepository.save(operation.getUser());
            operationRepository.save(operation);
        });
        budgetRepository.save(budgetOperation.getBudget());
        repository.save(budgetOperation);
        return budgetOperation;
    }

    @Test
    public void getById() throws Exception {
        BudgetOperation budgetOperation = saveForTest(ModelTestData.getBudgetOperation1());
        long id = budgetOperation.getId();

        budgetOperation = repository.findById(id).get();

        assertThat(budgetOperation).isNotNull();
        assertThat(budgetOperation.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        BudgetOperation budget = saveForTest(ModelTestData.getBudgetOperation1());

        repository.delete(budget);

        assertThat(repository.findById(budget.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestData.getBudgetOperation1());
        saveForTest(ModelTestData.getBudgetOperation2());
        Set<BudgetOperation> budgetOperations = new HashSet<>();

        repository.findAll().forEach(budgetOperations::add);

        assertThat(budgetOperations).isNotNull();
        assertThat(budgetOperations.size() == 2).isTrue();
    }
}