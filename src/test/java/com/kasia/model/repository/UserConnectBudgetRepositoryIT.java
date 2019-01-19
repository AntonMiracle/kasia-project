package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.UserConnectBudget;
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
public class UserConnectBudgetRepositoryIT {
    @Autowired
    private UserConnectBudgetRepository repository;
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
        UserConnectBudget userConnectBudget = ModelTestData.getUserConnectBudget1();
        assertThat(userConnectBudget.getId() == 0).isTrue();

        saveForTest(userConnectBudget);

        assertThat(userConnectBudget.getId() > 0).isTrue();
    }

    private UserConnectBudget saveForTest(UserConnectBudget userConnectBudget) {
        userConnectBudget.getConnectBudgets().forEach(budgetRepository::save);
        userRepository.save(userConnectBudget.getUser());
        repository.save(userConnectBudget);
        return userConnectBudget;
    }

    @Test
    public void getById() throws Exception {
        UserConnectBudget userConnectBudget = saveForTest(ModelTestData.getUserConnectBudget1());
        long id = userConnectBudget.getId();

        userConnectBudget = repository.findById(id).get();

        assertThat(userConnectBudget).isNotNull();
        assertThat(userConnectBudget.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        UserConnectBudget userConnectBudget = saveForTest(ModelTestData.getUserConnectBudget1());

        repository.delete(userConnectBudget);

        assertThat(repository.findById(userConnectBudget.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestData.getUserConnectBudget1());
        saveForTest(ModelTestData.getUserConnectBudget2());
        Set<UserConnectBudget> userConnectBudgets = new HashSet<>();

        repository.findAll().forEach(userConnectBudgets::add);

        assertThat(userConnectBudgets.size() == 2).isTrue();
    }
    @Test
    public void findByUserId() {
        UserConnectBudget expected = saveForTest(ModelTestData.getUserConnectBudget1());
        long userId = expected.getUser().getId();

        Optional<UserConnectBudget> actual = repository.findByUserId(userId);

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.orElse(null)).isEqualTo(expected);
    }
}