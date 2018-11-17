package com.kasia.model.service;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Article;
import com.kasia.model.User;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserModelServiceIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private UserModelService userService;
    private final String MAIL = "email@gmail.com";
    private final String MAIL_2 = "email22@gmail.com";
    private final String NICK = "nick";
    private final String NICK_2 = "nick22";
    private final String PASSWORD = "Password2";
    private final ZoneId ZONE_ID = ZoneId.systemDefault();

    @After
    public void after() {
        for (User u : userService.getAllUsers()) {
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
        assertThat(user.getBudgets()).isNotNull();
        assertThat(user.getEmployers()).isNotNull();
        assertThat(user.getArticles()).isNotNull();
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
    public void getArticlesByType() {
        User user = userService.create(MAIL, PASSWORD, NICK, ZONE_ID);
        user.getArticles().add(createArticle("name1", Article.Type.INCOME));
        user.getArticles().add(createArticle("name11", Article.Type.CONSUMPTION));
        user.getArticles().add(createArticle("name13", Article.Type.INCOME));
        user = userService.update(user);

        assertThat(userService.getArticlesByType(user, Article.Type.INCOME).size() == 2);
    }
    @Test
    public void cryptPassword() throws Exception {
        final String password = "Password2";
        final String crypt = "6f9dff5af05096ea9f23cc7bedd656830Aa";
        assertThat(userService.cryptPassword(password)).isEqualTo(crypt);
    }

    private Article createArticle(String name, Article.Type type) {
        Article article = new Article(name, "",type);
        article.setDescription("description");
        return article;
    }
}