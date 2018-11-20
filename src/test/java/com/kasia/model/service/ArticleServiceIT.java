package com.kasia.model.service;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.exception.OnUseRunTimeException;
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

public class ArticleServiceIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private ArticleService articleService;
    private final String DESCRIPTION_2 = "DESCRIPTION22";
    private final String NAME = "NAMe";
    private final Article.Type TYPE = Article.Type.INCOME;

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
        Article article = articleService.create(NAME, TYPE);

        assertThat(article.getId() > 0).isTrue();
        assertThat(article.getDescription()).isNotNull();
        assertThat(article.getType()).isEqualTo(TYPE);
        assertThat(article.getName()).isEqualTo(NAME);
    }

    @Test
    public void delete() throws Exception {
        Article article = articleService.create(NAME, TYPE);

        assertThat(articleService.getArticleById(article.getId())).isNotNull();
        articleService.delete(article.getId());

        assertThat(articleService.getArticleById(article.getId())).isNull();
    }

    @Inject
    private OperationService operationService;
    @Inject
    private UserService userService;
    @Inject
    private EmployerService employerService;

    @Test(expected = OnUseRunTimeException.class)
    public void whenDeleteArticleThrowOnUseRanTimeException() throws Exception {
        Article article = articleService.create(NAME, TYPE);
        User user = userService.create("email@gmail.com", "Password2", "REGEX_USER_NICK", ZoneId.systemDefault());
        Employer employer = employerService.create("SupN");
        Operation operation = operationService.create(BigDecimal.TEN, article, user, employer);

        articleService.delete(article.getId());
    }

    @Test
    public void update() throws Exception {
        Article article = articleService.create(NAME, TYPE);

        article.setDescription(DESCRIPTION_2);
        article = articleService.update(article);

        assertThat(article.getDescription()).isEqualTo(DESCRIPTION_2);
    }

    @Test
    public void getArticleById() throws Exception {
        Article article = articleService.create(NAME, TYPE);

        assertThat(articleService.getArticleById(article.getId())).isEqualTo(article);
    }

    @Test
    public void getAllArticles() {
        assertThat(articleService.getAllArticles()).isNotNull();
        assertThat(articleService.getAllArticles().size() == 0).isTrue();

        articleService.create(NAME, TYPE);
        articleService.create(NAME, TYPE);
        articleService.create(NAME, TYPE);

        assertThat(articleService.getAllArticles().size() == 3).isTrue();
    }
}