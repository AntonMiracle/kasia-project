package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.UserBudget;
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
public class UserBudgetRepositoryIT {
    @Autowired
    private UserBudgetRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BudgetRepository budgetRepository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
        budgetRepository.findAll().forEach(model -> budgetRepository.delete(model));
        userRepository.findAll().forEach(model -> userRepository.delete(model));
    }

    @Test
    public void save() {
        UserBudget userBudget = ModelTestData.getUserBudget1();
        assertThat(userBudget.getId() == 0).isTrue();

        saveForTest(userBudget);

        assertThat(userBudget.getId() > 0).isTrue();
    }

    private UserBudget saveForTest(UserBudget userBudget) {
        userBudget.getBudgets().forEach(budgetRepository::save);
        userRepository.save(userBudget.getUser());
        repository.save(userBudget);
        return userBudget;
    }

    @Test
    public void getById() throws Exception {
        UserBudget userBudget = saveForTest(ModelTestData.getUserBudget1());
        long id = userBudget.getId();

        userBudget = repository.findById(id).get();

        assertThat(userBudget).isNotNull();
        assertThat(userBudget.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        UserBudget userBudget = saveForTest(ModelTestData.getUserBudget1());

        repository.delete(userBudget);

        assertThat(repository.findById(userBudget.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestData.getUserBudget1());
        saveForTest(ModelTestData.getUserBudget2());
        Set<UserBudget> userBudgets = new HashSet<>();

        repository.findAll().forEach(userBudgets::add);

        assertThat(userBudgets.size() == 2).isTrue();
    }
}