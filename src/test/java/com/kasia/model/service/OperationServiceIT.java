package com.kasia.model.service;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Article;
import com.kasia.model.Employer;
import com.kasia.model.Operation;
import com.kasia.model.User;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.ZoneId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OperationServiceIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private OperationService operationService;
    @Inject
    private UserService userService;
    @Inject
    private EmployerService employerService;
    @Inject
    private ArticleService articleService;

    private final BigDecimal AMOUNT = BigDecimal.TEN;
    private final String NICK = "nick";
    private final String NICK_2 = "nick2";
    private final String NAME = "name";

    @After
    public void after() {
        for (Operation o : operationService.getAllOperations()) {
            operationService.delete(o.getId());
        }
        for (Article a : articleService.getAllArticles()) {
            articleService.delete(a.getId());
        }
        for (User u : userService.getAllUsers()) {
            userService.delete(u.getId());
        }
        for (Employer e : employerService.getAllEmployers()) {
            employerService.delete(e.getId());
        }
    }

    private User newUser() {
        return userService.create("email234@gmail.com", "Password22", "SuperNick2", ZoneId.systemDefault());
    }

    private Article newArticle() {
        return articleService.create(NAME, Article.Type.INCOME);
    }

    private Employer newEmployer() {
        return employerService.create(NAME);
    }

    @Test
    public void create() throws Exception {
        User user = newUser();
        Article article = newArticle();
        Employer employer = newEmployer();

        Operation operation = operationService.create(AMOUNT, article, user, employer);

        assertThat(operation).isNotNull();
        assertThat(operation.getEmployer()).isEqualTo(employer);
        assertThat(operation.getUserId()).isEqualTo(user.getId());
        assertThat(operation.getArticle()).isEqualTo(article);
    }

    @Test
    public void delete() throws Exception {
        User user = newUser();
        Article article = newArticle();
        Employer employer = newEmployer();
        Operation operation = operationService.create(AMOUNT, article, user, employer);

        assertThat(operationService.delete(operation.getId())).isTrue();
        assertThat(operationService.getOperationById(operation.getId())).isNull();
        assertThat(userService.getUserById(user.getId())).isNotNull();
        assertThat(employerService.getEmployerById(employer.getId())).isNotNull();
        assertThat(articleService.getArticleById(article.getId())).isNotNull();
    }

    @Test
    public void update() throws Exception {
        User user = newUser();
        Article article = newArticle();
        Employer employer = newEmployer();
        Operation operation = operationService.create(AMOUNT, article, user, employer);

        BigDecimal newAmount = AMOUNT.subtract(BigDecimal.ONE);
        operation.setAmount(newAmount);
        operation = operationService.update(operation);

        assertThat(operation.getAmount()).isEqualTo(newAmount);
    }

    @Test
    public void getOperationById() throws Exception {
        User user = newUser();
        Article article = newArticle();
        Employer employer = newEmployer();
        Operation operation = operationService.create(AMOUNT, article, user, employer);

        assertThat(operationService.getOperationById(operation.getId())).isEqualTo(operation);
    }

    @Test
    public void getOperationsByArticleId() throws Exception {
        User user = newUser();
        Article article = newArticle();
        Employer employer = newEmployer();
        Operation operation = operationService.create(AMOUNT, article, user, employer);

        assertThat(operationService.getOperationsByArticleId(operation.getArticle().getId()).size() == 1).isTrue();
    }

    @Test
    public void getOperationsByUserId() throws Exception {
        User user = newUser();
        Article article = newArticle();
        Employer employer = newEmployer();
        Operation operation = operationService.create(AMOUNT, article, user, employer);

        assertThat(operationService.getOperationsByUserId(operation.getUserId()).size() == 1).isTrue();
    }

    @Test
    public void getOperationsByEmployerId() throws Exception {
        User user = newUser();
        Article article = newArticle();
        Employer employer = newEmployer();
        Operation operation = operationService.create(AMOUNT, article, user, employer);

        assertThat(operationService.getOperationsByEmployerId(operation.getEmployer().getId()).size() == 1).isTrue();
    }

    @Test
    public void getAllOperations() throws Exception {
        assertThat(operationService.getAllOperations().size() == 0).isTrue();
        User user = newUser();
        Article article = newArticle();
        Employer employer = newEmployer();
        Operation operation = operationService.create(AMOUNT, article, user, employer);

        assertThat(operationService.getAllOperations().size() == 1).isTrue();
    }
}