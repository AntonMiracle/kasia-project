package com.kasia.service;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Article;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArticleServiceIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private ArticleService articleService;
    private final String DESCRIPTION = "DESCRIPTION";
    private final String DESCRIPTION_2 = "DESCRIPTION22";
    private final String NAME = "NAMe";
    private final String NAME_2 = "NAMe22";
    private final Article.Type TYPE = Article.Type.INCOME;
    private final BigDecimal AMOUNT = BigDecimal.TEN;

    @After
    public void after() {
        for (Article a : articleService.getAllArticles()) {
            articleService.delete(a.getId());
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