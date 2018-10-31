package com.kasia.repository;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Article;
import org.junit.After;
import org.junit.Test;

import javax.ejb.EJB;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArticleRepositoryIT extends ConfigurationEjbCdiContainerForIT {
    @EJB
    private ArticleRepository articleRepository;
    private final Article.Type TYPE_CONSUMTION = Article.Type.CONSUMPTION;
    private final String DESCRIPTION = "Some description";
    private final String DESCRIPTION_2 = "Some description22";
    private final String NAME = "name";

    @After
    public void after() {
        for (Article a : articleRepository.getAll()) {
            articleRepository.delete(a);
        }
    }

    @Test
    public void getById() throws Exception {
        Article article = new Article(NAME,TYPE_CONSUMTION);
        article.setDescription(DESCRIPTION);
        long id = articleRepository.save(article).getId();

        assertThat(articleRepository.getById(id)).isEqualTo(article);
    }

    @Test
    public void save() throws Exception {
        Article expected = new Article(NAME,TYPE_CONSUMTION);
        expected.setDescription(DESCRIPTION);

        long id = articleRepository.save(expected).getId();
        Article actual = articleRepository.getById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void delete() throws Exception {
        Article article = new Article(NAME,TYPE_CONSUMTION);
        article.setDescription(DESCRIPTION);
        long id = articleRepository.save(article).getId();

        assertThat(articleRepository.getById(id)).isNotNull();
        assertThat(articleRepository.delete(articleRepository.getById(id))).isTrue();
        assertThat(articleRepository.getById(id)).isNull();
    }

    @Test
    public void update() throws Exception {
        Article article = new Article(NAME,TYPE_CONSUMTION);
        article.setDescription(DESCRIPTION);
        long id = articleRepository.save(article).getId();
        article = articleRepository.getById(id);

        article.setDescription(DESCRIPTION_2);
        articleRepository.save(article);

        article = articleRepository.getById(id);
        assertThat(article.getDescription()).isEqualTo(DESCRIPTION_2);
    }

    @Test
    public void getAll() {
        Article article = new Article(NAME,TYPE_CONSUMTION);
        article.setDescription(DESCRIPTION);
        Article article1 = new Article(NAME,TYPE_CONSUMTION);
        article.setDescription(DESCRIPTION);
        articleRepository.save(article);
        articleRepository.save(article1);

        assertThat(articleRepository.getAll().size() == 2).isTrue();
    }

}