package com.kasia.repository;

import com.kasia.model.Article;
import com.kasia.model.Budget;
import com.kasia.model.Economy;
import com.kasia.model.User;
import com.kasia.repository.imp.UserRepositoryImp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Currency;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserRepositoryIT extends RepositoryITHelper {
    private UserRepository userRepository;
    private User user;
    private final String EMAIL = "email@gmail.com";
    private final String NICK = "nick";
    private final String PASSWORD = "pass";
    private final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    private final ZoneId ZONE_ID = ZoneId.systemDefault();


    @Before
    public void before() {
        userRepository = new UserRepositoryImp(repositoryConnectionService.getEntityManager());
        user = new User();
    }

    @After
    public void after() {
        for (User u : userRepository.getAll()) {
            userRepository.delete(u);
        }
    }

    @Test
    public void getById() throws Exception {
        user.setCreateOn(LOCAL_DATE_TIME);
        user.setNick(NICK);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setZoneId(ZONE_ID);
        user.setRole(User.Role.USER);

        long id = userRepository.save(user).getId();

        assertThat(userRepository.getById(id).getNick()).isEqualTo(NICK);
    }

    @Test
    public void getByEmail() throws Exception {
        user.setCreateOn(LOCAL_DATE_TIME);
        user.setNick(NICK);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setZoneId(ZONE_ID);
        user.setRole(User.Role.USER);

        long id = userRepository.save(user).getId();

        assertThat(userRepository.getByEmail(EMAIL).getId()).isEqualTo(id);
    }

    @Test
    public void getByNick() throws Exception {
        user.setCreateOn(LOCAL_DATE_TIME);
        user.setNick(NICK);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setZoneId(ZONE_ID);
        user.setRole(User.Role.USER);

        long id = userRepository.save(user).getId();

        assertThat(userRepository.getByNick(NICK).getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        user.setCreateOn(LOCAL_DATE_TIME);
        user.setNick(NICK);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setZoneId(ZONE_ID);
        user.setRole(User.Role.USER);

        long id = userRepository.save(user).getId();
        assertThat(id > 0).isTrue();

        userRepository.delete(user);
        assertThat(userRepository.getById(id)).isNull();
    }

    @Test
    public void update() throws Exception {
        Article article = new Article();
        article.setAmount(BigDecimal.TEN);
        article.setType(Article.Type.INCOME);
        article.setDescription("text");
        LocalDateTime date = LocalDateTime.now();
        article.setCreateOn(date);

        Budget budget = new Budget();
        budget.setBalance(BigDecimal.TEN);
        budget.setCurrency(Currency.getInstance("EUR"));
        budget.setArticles(new HashSet<>());
        budget.getArticles().add(article);
        budget.setName("budgetName");
        budget.setCreateOn(LocalDateTime.now());

        Economy economy = new Economy();
        economy.setBudgets(new HashSet<>());
        economy.getBudgets().add(budget);
        economy.setName("Economy");
        economy.setCreateOn(LocalDateTime.now());

        user.setCreateOn(LOCAL_DATE_TIME);
        user.setNick(NICK);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setZoneId(ZONE_ID);
        user.setEconomies(new HashSet<>());
        user.setRole(User.Role.USER);

        long id = userRepository.save(user).getId();
        assertThat(id > 0).isTrue();

        user.getEconomies().add(economy);
        userRepository.update(user);

        for (Economy eco : user.getEconomies()) {
            assertThat(eco.getId() > 0).isTrue();
        }
    }

    @Test
    public void save() throws Exception {
        user.setCreateOn(LOCAL_DATE_TIME);
        user.setNick(NICK);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setZoneId(ZONE_ID);
        user.setRole(User.Role.USER);

        userRepository.save(user);
        assertThat(user.getId() > 0).isTrue();

        user = userRepository.getById(user.getId());
        assertThat(user.getRole()).isEqualTo(User.Role.USER);
        assertThat(user.getCreateOn()).isEqualTo(LOCAL_DATE_TIME);
        assertThat(user.getZoneId()).isEqualTo(ZONE_ID);
        assertThat(user.getNick()).isEqualTo(NICK);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    public void getAll() {
        user.setCreateOn(LOCAL_DATE_TIME);
        user.setNick(NICK);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setZoneId(ZONE_ID);
        user.setRole(User.Role.USER);

        User user1 = new User();
        user1.setCreateOn(LOCAL_DATE_TIME);
        user1.setNick(NICK + "1");
        user1.setPassword(PASSWORD);
        user1.setEmail(EMAIL);
        user1.setZoneId(ZONE_ID);
        user1.setRole(User.Role.USER);

        userRepository.save(user);
        userRepository.save(user1);

        assertThat(userRepository.getAll().size() == 2).isTrue();
    }
}