package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.BudgetOperation;
import com.kasia.model.User;
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
public class BudgetOperationRepositoryIT {
    @Autowired
    private BudgetOperationRepository repository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private ElementRepository elementRepository;
    @Autowired
    private PlaceRepository placeRepository;
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
        placeRepository.findAll().forEach(model -> placeRepository.delete(model));
    }

    @Test
    public void save() {
        BudgetOperation budgetOperation = ModelTestData.budgetOperation();
        assertThat(budgetOperation.getId() == 0).isTrue();

        saveForTest(budgetOperation);

        assertThat(budgetOperation.getId() > 0).isTrue();
    }

    private BudgetOperation saveForTest(BudgetOperation budgetOperation) {
        budgetOperation.getOperations().forEach(operation ->
        {
            elementRepository.save(operation.getElement());
            placeRepository.save(operation.getPlace());
            Optional<User> user = userRepository.findByEmail(operation.getUser().getEmail());
            if (user.isPresent()) {
                operation.setUser(user.get());
            } else {
                userRepository.save(operation.getUser());
            }
            operationRepository.save(operation);
        });
        budgetRepository.save(budgetOperation.getBudget());
        repository.save(budgetOperation);
        return budgetOperation;
    }

    @Test
    public void findById() throws Exception {
        BudgetOperation budgetOperation = saveForTest(ModelTestData.budgetOperation());
        long id = budgetOperation.getId();

        budgetOperation = repository.findById(id).get();

        assertThat(budgetOperation).isNotNull();
        assertThat(budgetOperation.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        BudgetOperation budget = saveForTest(ModelTestData.budgetOperation());

        repository.delete(budget);

        assertThat(repository.findById(budget.getId()).isPresent()).isFalse();
    }

    @Test
    public void findAll() throws Exception {
        saveForTest(ModelTestData.budgetOperation());
        saveForTest(ModelTestData.budgetOperation());
        Set<BudgetOperation> budgetOperations = new HashSet<>();

        repository.findAll().forEach(budgetOperations::add);

        assertThat(budgetOperations).isNotNull();
        assertThat(budgetOperations.size() == 2).isTrue();
    }

    @Test
    public void findByBudgetId() {
        BudgetOperation expected = saveForTest(ModelTestData.budgetOperation());

        assertThat(repository.findByBudgetId(expected.getBudget().getId()).get()).isEqualTo(expected);
    }
}