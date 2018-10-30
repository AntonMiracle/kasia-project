package com.kasia.service;

import com.kasia.model.Economy;
import com.kasia.model.User;
import com.kasia.repository.RepositoryITHelper;
import com.kasia.service.imp.UserServiceImp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserServiceIT extends RepositoryITHelper {
    private UserService userService;
    private User user;
    private final String MAIL = "email@gmail.com";
    private final String NICK = "nick";
    private final String PASSWORD = "password";
    private final ZoneId ZONE_ID = ZoneId.systemDefault();

    @Before
    public void before() {
        userService = new UserServiceImp(new UserRepositoryImp(repositoryConnectionService.getEntityManager()));
        user = new User();
    }

    @After
    public void after() {
        for (User u : userService.getAll()) {
            userService.delete(u.getId());
        }
    }

    @Test
    public void create() throws Exception {
        long id = userService.create(MAIL, PASSWORD, NICK, ZONE_ID).getId();
        assertThat(id != 0).isTrue();
        user = userService.getUserById(id);
        assertThat(user.getPassword()).isEqualTo(userService.cryptPassword(PASSWORD));
        assertThat(user.getEmail()).isEqualTo(MAIL);
        assertThat(user.getNick()).isEqualTo(NICK);
        assertThat(user.getZoneId()).isEqualTo(ZONE_ID);
    }

    @Test
    public void update() throws Exception {
        String newMail = "newMail@gmail.com";
        user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);
        long id = user.getId();
        user.setEmail(newMail);
        userService.update(user);
        assertThat(userService.getUserById(id)).isEqualTo(user);
    }

    @Test
    public void delete() throws Exception {
        assertThat(userService.getAll().size() == 0).isTrue();
        user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);
        long id = user.getId();
        assertThat(userService.getAll().size() == 1).isTrue();

        userService.delete(id);
        assertThat(userService.getAll().size() == 0).isTrue();
    }

    @Test
    public void getUserById() throws Exception {
        user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);
        long id = user.getId();
        assertThat(id != 0).isTrue();
        assertThat(userService.getUserById(id)).isEqualTo(user);
    }

    @Test
    public void getByEmail() throws Exception {
        user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);
        assertThat(userService.getByEmail(MAIL)).isEqualTo(user);
    }

    @Test
    public void getByNick() throws Exception {
        user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);
        assertThat(userService.getByNick(NICK)).isEqualTo(user);
    }

    @Test
    public void addEconomic() throws Exception {
        user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);
        assertThat(user.getEconomies().size() == 0).isTrue();

        userService.addEconomic(user, createEconomy());
        assertThat(user.getEconomies().size() == 1).isTrue();

        for (Economy e : user.getEconomies()) {
            assertThat(e.getId() > 0).isTrue();
        }
    }

    @Test
    public void removeEconomic() throws Exception {
        user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);
        userService.addEconomic(user, createEconomy());
        assertThat(user.getEconomies().size() == 1).isTrue();

        for (Economy e : user.getEconomies()) {
            userService.removeEconomic(user, e);
        }
        assertThat(user.getEconomies().size() == 0).isTrue();
    }

    private Economy createEconomy() {
        Economy economy = new Economy();
        economy.setName("name");
        economy.setCreateOn(LocalDateTime.now());
        economy.setBudgets(new HashSet<>());
        return economy;
    }

    @Test
    public void cryptPassword() throws Exception {
        final String password = "password";
        final String crypt = "5f4dcc3b5aa765d61d8327deb882cf99";
        assertThat(userService.cryptPassword(password)).isEqualTo(crypt);
    }

}