package com.kasia.repository;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Economy;
import com.kasia.model.User;
import org.junit.After;
import org.junit.Test;

import javax.ejb.EJB;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserRepositoryIT extends ConfigurationEjbCdiContainerForIT {
    @EJB
    private UserRepository userRepository;
    private final String EMAIL = "email@gmail.com";
    private final String EMAIL_2 = "email22@gmail.com";
    private final String NICK = "nick";
    private final String NICK_2 = "nick2";
    private final String PASSWORD = "pass";
    private final LocalDateTime CREATE_ON = LocalDateTime.of(2020, 10, 10, 10, 10, 10);
    private final ZoneId ZONE_ID = ZoneId.systemDefault();
    private final User.Role ROLE = User.Role.USER;

    @After
    public void after() {
        for (User u : userRepository.getAll()) {
            userRepository.delete(u);
        }
    }

    @Test
    public void getById() throws Exception {
        User user = new User(ROLE, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        user.setEconomies(new HashSet<>());
        long id = userRepository.save(user).getId();

        assertThat(userRepository.getById(id)).isEqualTo(user);
    }

    @Test
    public void getAll() throws Exception {
        User user = new User(ROLE, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        user.setEconomies(new HashSet<>());
        User user1 = new User(ROLE, EMAIL_2, NICK_2, PASSWORD, ZONE_ID, CREATE_ON);
        user.setEconomies(new HashSet<>());
        userRepository.save(user);
        userRepository.save(user1);

        assertThat(userRepository.getAll().size() == 2).isTrue();
    }

    @Test
    public void delete() throws Exception {
        User user = new User(ROLE, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        user.setEconomies(new HashSet<>());
        long id = userRepository.save(user).getId();

        assertThat(userRepository.getById(id)).isNotNull();
        assertThat(userRepository.delete(userRepository.getById(id))).isTrue();
        assertThat(userRepository.getById(id)).isNull();
    }

    @Test
    public void save() throws Exception {
        User expected = new User(ROLE, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        expected.setEconomies(new HashSet<>());

        long id = userRepository.save(expected).getId();
        User actual = userRepository.getById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByEmail() throws Exception {
        User expected = new User(ROLE, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        expected.setEconomies(new HashSet<>());
        String email = userRepository.save(expected).getEmail();

        User actual = userRepository.getByEmail(email);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByNick() throws Exception {
        User expected = new User(ROLE, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        expected.setEconomies(new HashSet<>());
        String nick = userRepository.save(expected).getNick();

        User actual = userRepository.getByNick(nick);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        User user = new User(ROLE, EMAIL, NICK, PASSWORD, ZONE_ID, CREATE_ON);
        user.setEconomies(new HashSet<>());
        long id = userRepository.save(user).getId();
        user = userRepository.getById(id);

        assertThat(user.getEconomies().size() == 0).isTrue();
        Economy economy = new Economy();
        economy.setBudgets(new HashSet<>());
        economy.setName("Economy");
        economy.setCreateOn(CREATE_ON);
        user.getEconomies().add(economy);
        userRepository.save(user);

        user = userRepository.getById(id);
        for (Economy eco : user.getEconomies()) {
            assertThat(eco.getId() > 0).isTrue();
        }
        assertThat(user.getEconomies().size() == 1).isTrue();
    }
}