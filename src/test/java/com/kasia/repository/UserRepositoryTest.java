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

public class UserRepositoryTest extends RepositoryTestHelper {
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
        if (userRepository != null && userRepository.getByNick(NICK) != null) {
            user = userRepository.getByNick(NICK);
            userRepository.delete(user);
        }
        if (userRepository != null && userRepository.getByEmail(EMAIL) != null) {
            user = userRepository.getByEmail(EMAIL);
            userRepository.delete(user);
        }
    }

    @Test
    public void getById() throws Exception {
        user.setCreateOn(LOCAL_DATE_TIME);
        user.setNick(NICK);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setZoneId(ZONE_ID);

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

        userRepository.save(user);
        assertThat(user.getId() > 0).isTrue();
    }

}