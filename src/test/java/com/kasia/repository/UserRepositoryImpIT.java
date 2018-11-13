package com.kasia.repository;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Budget;
import com.kasia.model.User;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserRepositoryImpIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private UserRepository repository;
    private final String EMAIL = "email@gmail.com";
    private final String EMAIL_2 = "email22@gmail.com";
    private final String NICK = "nick";
    private final String NICK_2 = "nick2";
    private final String PASSWORD = "Password2";
    private final LocalDateTime CREATE_ON = LocalDateTime.of(2020, 10, 10, 10, 10, 10);
    private final ZoneId ZONE_ID = ZoneId.systemDefault();
    private final User.Role ROLE = User.Role.USER;

    @After
    public void after() {
        for (User u : repository.getAll()) {
            repository.delete(u);
        }
    }

    @Test
    public void getById() throws Exception {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);
        User user = new User(roles, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        user.setBudgets(new HashSet<>());
        user.setEmployers(new HashSet<>());
        user.setArticles(new HashSet<>());
        long id = repository.save(user).getId();

        assertThat(repository.getById(id)).isEqualTo(user);
    }

    @Test
    public void getAll() throws Exception {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);
        User user = new User(roles, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        user.setBudgets(new HashSet<>());
        User user1 = new User(roles, EMAIL_2, NICK_2, PASSWORD, ZONE_ID, CREATE_ON);
        user1.setBudgets(new HashSet<>());
        repository.save(user);
        repository.save(user1);

        assertThat(repository.getAll().size() == 2).isTrue();
    }

    @Test
    public void delete() throws Exception {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);
        User user = new User(roles, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        user.setBudgets(new HashSet<>());
        long id = repository.save(user).getId();

        assertThat(repository.getById(id)).isNotNull();
        assertThat(repository.delete(repository.getById(id))).isTrue();
        assertThat(repository.getById(id)).isNull();
    }

    @Test
    public void save() throws Exception {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);
        User expected = new User(roles, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        expected.setBudgets(new HashSet<>());
        expected.setEmployers(new HashSet<>());
        expected.setArticles(new HashSet<>());

        long id = repository.save(expected).getId();
        User actual = repository.getById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByEmail() throws Exception {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);
        User expected = new User(roles, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        expected.setBudgets(new HashSet<>());
        expected.setEmployers(new HashSet<>());
        expected.setArticles(new HashSet<>());
        String email = repository.save(expected).getEmail();

        User actual = repository.getByEmail(email);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByNick() throws Exception {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);
        User expected = new User(roles, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        expected.setBudgets(new HashSet<>());
        expected.setEmployers(new HashSet<>());
        expected.setArticles(new HashSet<>());
        String nick = repository.save(expected).getNick();

        User actual = repository.getByNick(nick);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);
        User user = new User(roles, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        user.setBudgets(new HashSet<>());
        long id = repository.save(user).getId();
        user = repository.getById(id);

        assertThat(user.getBudgets().size() == 0).isTrue();
        Budget budget = new Budget("Name", BigDecimal.TEN, Currency.getInstance("EUR"), CREATE_ON);
        budget.setOperations(new HashSet<>());
        user.getBudgets().add(budget);
        repository.save(user);

        user = repository.getById(id);
        for (Budget b : user.getBudgets()) {
            assertThat(b.getId() > 0).isTrue();
        }
        assertThat(user.getBudgets().size() == 1).isTrue();
    }

    @Test
    public void userHasTwoRoles() {
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);
        roles.add(User.Role.ADMINISTRATOR);
        User user = new User(roles, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        user.setBudgets(new HashSet<>());
        long id = repository.save(user).getId();
        user = repository.getById(id);

        assertThat(user.getRoles().size() == 2).isTrue();
    }
}