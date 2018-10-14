package com.kasia.service;

import com.kasia.model.Article;
import com.kasia.repository.RepositoryTestHelper;
import com.kasia.repository.imp.ArticleRepositoryImp;
import com.kasia.service.imp.ArticleServiceImp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArticleServiceTest extends RepositoryTestHelper {
    private ArticleService articleService;
    private Article article;
    private final String DESCRIPTION = "DESCRIPTION";
    private final Article.Type TYPE = Article.Type.INCOME;
    private final BigDecimal AMOUNT = BigDecimal.TEN;

    @Before
    public void before() {
        articleService = new ArticleServiceImp(new ArticleRepositoryImp(repositoryConnectionService.getEntityManager()));
    }

    @After
    public void after() {
        for (Article a : articleService.getAllArticles()) {
            System.out.println(a.getId());
            articleService.delete(a.getId());
        }

    }

    @Test
    public void create() throws Exception {
        article = articleService.create(DESCRIPTION, TYPE, AMOUNT);
        assertThat(article.getId() > 0).isTrue();
        assertThat(article.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(article.getType()).isEqualTo(TYPE);
        assertThat(article.getAmount()).isEqualTo(AMOUNT);
        assertThat(article.getCreateOn()).isBefore(LocalDateTime.now());
    }

    @Test
    public void delete() throws Exception {
        article = articleService.create(DESCRIPTION, TYPE, AMOUNT);
        long id = article.getId();
        assertThat(articleService.getArticleById(id)).isNotNull();
        articleService.delete(id);
        assertThat(articleService.getArticleById(id)).isNull();
    }

    @Test
    public void update() throws Exception {
        article = articleService.create(DESCRIPTION, TYPE, AMOUNT);
        article.setDescription("hello");
        long id = article.getId();
        articleService.update(article);
        assertThat(articleService.getArticleById(id).getDescription()).isEqualTo("hello");
    }

    @Test
    public void getArticleById() throws Exception {
        article = articleService.create(DESCRIPTION, TYPE, AMOUNT);
        long id = article.getId();
        assertThat(articleService.getArticleById(id).getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    public void getAllArticles() {
        assertThat(articleService.getAllArticles()).isNotNull();

        articleService.create(DESCRIPTION, TYPE, AMOUNT);
        articleService.create(DESCRIPTION + "1", TYPE, BigDecimal.ZERO);
        articleService.create(DESCRIPTION + "2", TYPE, BigDecimal.valueOf(123));

//        assertThat(articleService.getAllArticles().size() == 3).isTrue();

    }
}