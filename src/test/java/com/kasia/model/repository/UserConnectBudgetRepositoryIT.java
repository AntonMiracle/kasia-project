package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.Budget;
import com.kasia.model.User;
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
    private UserConnectBudgetRepository ucbRepository;
    @Autowired
    private UserRepository uRepository;
    @Autowired
    private BudgetRepository bRepository;

    @After
    public void cleanData() {
        ucbRepository.findAll().forEach(model -> ucbRepository.delete(model));
        bRepository.findAll().forEach(model -> bRepository.delete(model));
        uRepository.findAll().forEach(model -> uRepository.delete(model));
    }

    @Test
    public void save() {
        UserConnectBudget userConnectBudget = ModelTestData.userConnectBudget();
        assertThat(userConnectBudget.getId() == 0).isTrue();

        saveForTest(userConnectBudget);

        assertThat(userConnectBudget.getId() > 0).isTrue();
    }

    @Test
    public void saveWithSameBudget() {
        Budget budget1 = bRepository.save(ModelTestData.budget());

        UserConnectBudget ucb1 = ModelTestData.userConnectBudget();
        UserConnectBudget ucb2 = ModelTestData.userConnectBudget();

        ucb1.getConnectBudgets().clear();
        ucb2.getConnectBudgets().clear();
        ucb1.getConnectBudgets().add(budget1);
        ucb2.getConnectBudgets().add(budget1);

        User user1 = ModelTestData.user();
        User user2 = ModelTestData.user();
        user2.setEmail("new" + user1.getEmail());

        ucb1.setUser(uRepository.save(user1));
        ucb2.setUser(uRepository.save(user2));

        assertThat(ucb1.getId() == 0).isTrue();
        assertThat(ucb2.getId() == 0).isTrue();

        ucbRepository.save(ucb1);
        ucbRepository.save(ucb2);

        assertThat(ucb1.getId() > 0).isTrue();
        assertThat(ucb2.getId() > 0).isTrue();
    }

    private UserConnectBudget saveForTest(UserConnectBudget userConnectBudget) {
        userConnectBudget.getConnectBudgets().forEach(bRepository::save);
        uRepository.save(userConnectBudget.getUser());
        ucbRepository.save(userConnectBudget);
        return userConnectBudget;
    }

    @Test
    public void findById() throws Exception {
        UserConnectBudget userConnectBudget = saveForTest(ModelTestData.userConnectBudget());
        long id = userConnectBudget.getId();

        userConnectBudget = ucbRepository.findById(id).orElse(null);

        assertThat(userConnectBudget).isNotNull();
        assertThat(userConnectBudget.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        UserConnectBudget userConnectBudget = saveForTest(ModelTestData.userConnectBudget());

        ucbRepository.delete(userConnectBudget);

        assertThat(ucbRepository.findById(userConnectBudget.getId()).isPresent()).isFalse();
    }

    @Test
    public void findAll() throws Exception {
        User user1 = ModelTestData.user();
        User user2 = ModelTestData.user();
        user2.setEmail("new" + user1.getEmail());

        UserConnectBudget ucb1 = ModelTestData.userConnectBudget();
        UserConnectBudget ucb2 = ModelTestData.userConnectBudget();
        ucb1.setUser(user1);
        ucb2.setUser(user2);

        saveForTest(ucb1);
        saveForTest(ucb2);
        Set<UserConnectBudget> userConnectBudgets = new HashSet<>();

        ucbRepository.findAll().forEach(userConnectBudgets::add);

        assertThat(userConnectBudgets.size() == 2).isTrue();
    }

    @Test
    public void findByUserId() {
        UserConnectBudget expected = saveForTest(ModelTestData.userConnectBudget());
        long userId = expected.getUser().getId();

        Optional<UserConnectBudget> actual = ucbRepository.findByUserId(userId);

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.orElse(null)).isEqualTo(expected);
    }
}