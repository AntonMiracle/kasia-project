package com.kasia.repository;

import com.kasia.model.Article;
import com.oneandone.ejbcdiunit.EjbUnitRunner;
import com.oneandone.ejbcdiunit.persistence.TestPersistenceFactory;
import org.jglue.cdiunit.AdditionalClasses;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(EjbUnitRunner.class)
@AdditionalClasses({ArticleRepository.class, TestPersistenceFactory.class})
public class ArticleRepositoryIT {
    @EJB
    private ArticleRepository articleRepository;
    private final LocalDateTime CREATE_ON = LocalDateTime.of(2020, 10, 10, 10, 10, 10);
    private final BigDecimal AMOUNT = BigDecimal.TEN;
    private final Article.Type TYPE_CONSUMTION = Article.Type.CONSUMPTION;
    private final String DESCRIPTION = "Some description";
    private final String DESCRIPTION_2 = "Some description22";

    @After
    public void after() {
        for (Article a : articleRepository.getAll()) {
            articleRepository.delete(a);
        }
    }

    @Test
    public void getById() throws Exception {
        Article article = new Article(TYPE_CONSUMTION, AMOUNT, CREATE_ON);
        article.setDescription(DESCRIPTION);
        long id = articleRepository.save(article).getId();

        assertThat(articleRepository.getById(id)).isEqualTo(article);
    }

    @Test
    public void save() throws Exception {
        Article expected = new Article(TYPE_CONSUMTION, AMOUNT, CREATE_ON);
        expected.setDescription(DESCRIPTION);

        long id = articleRepository.save(expected).getId();
        Article actual = articleRepository.getById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void delete() throws Exception {
        Article article = new Article(TYPE_CONSUMTION, AMOUNT, CREATE_ON);
        article.setDescription(DESCRIPTION);
        long id = articleRepository.save(article).getId();

        assertThat(articleRepository.getById(id)).isNotNull();
        assertThat(articleRepository.delete(articleRepository.getById(id))).isTrue();
        assertThat(articleRepository.getById(id)).isNull();
    }

    @Test
    public void update() throws Exception {
        Article article = new Article(TYPE_CONSUMTION, AMOUNT, CREATE_ON);
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
        Article article = new Article(TYPE_CONSUMTION, AMOUNT, CREATE_ON);
        article.setDescription(DESCRIPTION);
        Article article1 = new Article(TYPE_CONSUMTION, AMOUNT, CREATE_ON);
        article.setDescription(DESCRIPTION);
        articleRepository.save(article);
        articleRepository.save(article1);

        assertThat(articleRepository.getAll().size() == 2).isTrue();
    }

}