package com.kasia.model.repository;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Budget;
import com.kasia.model.User;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserRepositoryIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private UserRepository repository;
    private final String EMAIL = "email@gmail.com";
    private final String EMAIL_2 = "email22@gmail.com";
    private final String NICK = "nick";
    private final String NICK_2 = "nick2";



    @After
    public void after() {
        for (User u : repository.getAll()) {
            repository.delete(u);
        }
    }

    @Test
    public void getById() throws Exception {
        User user = createUser(EMAIL, NICK);
        long id = repository.save(user).getId();

        assertThat(repository.getById(id)).isEqualTo(user);
    }

    @Test
    public void getAll() throws Exception {
        User user = createUser(EMAIL, NICK);
        User user1 = createUser(EMAIL_2, NICK_2);
        repository.save(user);
        repository.save(user1);

        assertThat(repository.getAll().size() == 2).isTrue();
    }

    @Test
    public void delete() throws Exception {
        User user = createUser(EMAIL, NICK);
        long id = repository.save(user).getId();

        assertThat(repository.getById(id)).isNotNull();
        assertThat(repository.delete(repository.getById(id))).isTrue();
        assertThat(repository.getById(id)).isNull();
    }

    @Test
    public void save() throws Exception {
        User expected = createUser(EMAIL, NICK);

        long id = repository.save(expected).getId();
        User actual = repository.getById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByEmail() throws Exception {
        User expected = createUser(EMAIL, NICK);
        String email = repository.save(expected).getEmail();

        User actual = repository.getByEmail(email);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByNick() throws Exception {
        User expected = createUser(EMAIL, NICK);
        String nick = repository.save(expected).getNick();

        User actual = repository.getByNick(nick);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        User user = createUser(EMAIL, NICK);
        long id = repository.save(user).getId();
        user = repository.getById(id);

        assertThat(user.getBudgets().size() == 0).isTrue();
        Budget budget = new Budget("Name", new HashSet<>(),BigDecimal.TEN,  LocalDateTime.now(),Currency.getInstance("EUR"));
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
        User user = createUser(EMAIL, NICK);
        user.getRoles().add(User.Role.ADMINISTRATOR);
        long id = repository.save(user).getId();
        user = repository.getById(id);

        assertThat(user.getRoles().size() == 2).isTrue();
    }
}