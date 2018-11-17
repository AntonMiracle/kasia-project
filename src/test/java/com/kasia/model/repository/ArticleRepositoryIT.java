package com.kasia.model.repository;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Article;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArticleRepositoryIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private ArticleRepository repository;
    private final Article.Type TYPE_CONSUMTION = Article.Type.CONSUMPTION;
    private final String DESCRIPTION = "Some description";
    private final String DESCRIPTION_2 = "Some description22";
    private final String NAME = "name";

    @After
    public void after() {
        for (Article a : repository.getAll()) {
            repository.delete(a);
        }
    }

    @Test
    public void getById() throws Exception {
        Article article = new Article(NAME, "", TYPE_CONSUMTION);
        article.setDescription(DESCRIPTION);
        long id = repository.save(article).getId();

        assertThat(repository.getById(id)).isEqualTo(article);
    }

    @Test
    public void save() throws Exception {
        Article expected = new Article(NAME, "", TYPE_CONSUMTION);
        expected.setDescription(DESCRIPTION);

        long id = repository.save(expected).getId();
        Article actual = repository.getById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void delete() throws Exception {
        Article article = new Article(NAME, "", TYPE_CONSUMTION);
        article.setDescription(DESCRIPTION);
        long id = repository.save(article).getId();

        assertThat(repository.getById(id)).isNotNull();
        assertThat(repository.delete(repository.getById(id))).isTrue();
        assertThat(repository.getById(id)).isNull();
    }

    @Test
    public void update() throws Exception {
        Article article = new Article(NAME, "", TYPE_CONSUMTION);
        article.setDescription(DESCRIPTION);
        long id = repository.save(article).getId();
        article = repository.getById(id);

        article.setDescription(DESCRIPTION_2);
        repository.save(article);

        article = repository.getById(id);
        assertThat(article.getDescription()).isEqualTo(DESCRIPTION_2);
    }

    @Test
    public void getAll() {
        Article article = new Article(NAME, "", TYPE_CONSUMTION);
        article.setDescription(DESCRIPTION);
        Article article1 = new Article(NAME, "", TYPE_CONSUMTION);
        article.setDescription(DESCRIPTION);
        repository.save(article);
        repository.save(article1);

        assertThat(repository.getAll().size() == 2).isTrue();
    }

}