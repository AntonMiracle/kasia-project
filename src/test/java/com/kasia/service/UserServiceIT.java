package com.kasia.service;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.User;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserServiceIT extends ConfigurationEjbCdiContainerForIT{
    @Inject
    private UserService userService;
    private final String MAIL = "email@gmail.com";
    private final String MAIL_2 = "email22@gmail.com";
    private final String NICK = "nick";
    private final String NICK_2 = "nick22";
    private final String PASSWORD = "password";
    private final ZoneId ZONE_ID = ZoneId.systemDefault();

    @After
    public void after() {
        for (User u : userService.getAll()) {
            userService.delete(u.getId());
        }
    }

    @Test
    public void create() throws Exception {
        User user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);
        String cryptPassword = userService.cryptPassword(PASSWORD);

        assertThat(user.getId() > 0).isTrue();
        assertThat(user.getPassword()).isEqualTo(cryptPassword);
        assertThat(user.getEmail()).isEqualTo(MAIL);
        assertThat(user.getNick()).isEqualTo(NICK);
        assertThat(user.getZoneId()).isEqualTo(ZONE_ID);
        assertThat(user.getCreateOn()).isBefore(LocalDateTime.now());
    }

    @Test
    public void update() throws Exception {
        User user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);

        user.setEmail(MAIL_2);
        user = userService.update(user);

        assertThat(user.getEmail()).isEqualTo(MAIL_2);
    }

    @Test
    public void delete() throws Exception {
        User user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);

        assertThat(userService.getUserById(user.getId())).isNotNull();
        userService.delete(user.getId());

        assertThat(userService.getUserById(user.getId())).isNull();
    }

    @Test
    public void getUserById() throws Exception {
        User user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);

        assertThat(userService.getUserById(user.getId())).isEqualTo(user);
    }

    @Test
    public void getByEmail() throws Exception {
        User user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);

        assertThat(userService.getByEmail(MAIL)).isEqualTo(user);
    }

    @Test
    public void getByNick() throws Exception {
        User user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);

        assertThat(userService.getByNick(NICK)).isEqualTo(user);
    }

    @Test
    public void cryptPassword() throws Exception {
        final String password = "password";
        final String crypt = "5f4dcc3b5aa765d61d8327deb882cf99";
        assertThat(userService.cryptPassword(password)).isEqualTo(crypt);
    }

}