package com.kasia.service;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.exception.OnUseRunTimeException;
import com.kasia.model.Article;
import com.kasia.model.Employer;
import com.kasia.model.Operation;
import com.kasia.model.User;
import com.kasia.service.model.ArticleService;
import com.kasia.service.model.EmployerService;
import com.kasia.service.model.OperationService;
import com.kasia.service.model.UserService;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.time.ZoneId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EmployerServiceIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private EmployerService employerService;
    private final String NAME = "name";
    private final String NAME_2 = "name2";

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

    @Test
    public void create() throws Exception {
        Employer employer = employerService.create(NAME);

        assertThat(employer.getId() > 0).isTrue();
        assertThat(employer.getName()).isEqualTo(NAME);
        assertThat(employer.getDescription()).isNotNull();
    }

    @Test
    public void delete() throws Exception {
        long id = employerService.create(NAME).getId();

        assertThat(employerService.getEmployerById(id)).isNotNull();
        assertThat(employerService.delete(id)).isTrue();
        assertThat(employerService.getEmployerById(id)).isNull();
    }

    @Inject
    private OperationService operationService;
    @Inject
    private UserService userService;
    @Inject
    private ArticleService articleService;

    @Test(expected = OnUseRunTimeException.class)
    public void whenDeleteArticleThrowOnUseRanTimeException() throws Exception {
        Article article = articleService.create(NAME, Article.Type.INCOME);
        User user = userService.create("email@gmail.com", "Password2", "NICK", ZoneId.systemDefault());
        Employer employer = employerService.create("SupN");
        Operation operation = operationService.create(BigDecimal.TEN, article, user, employer);

        employerService.delete(employer.getId());
    }

    @Test
    public void update() throws Exception {
        Employer employer = employerService.create(NAME);

        employer.setName(NAME_2);
        employer = employerService.update(employer);

        assertThat(employer.getName()).isEqualTo(NAME_2);
    }

    @Test
    public void getEmployerById() throws Exception {
        Employer employer = employerService.create(NAME);

        assertThat(employerService.getEmployerById(employer.getId())).isEqualTo(employer);
    }

    @Test
    public void getEmployerByName() throws Exception {
        Employer employer = employerService.create(NAME);

        assertThat(employerService.getEmployerByName(employer.getName())).isEqualTo(employer);
    }

    @Test
    public void getAllEmployers() throws Exception {
        assertThat(employerService.getAllEmployers().size() == 0);

        employerService.create(NAME);
        employerService.create(NAME_2);

        assertThat(employerService.getAllEmployers().size() == 2).isTrue();
    }
}