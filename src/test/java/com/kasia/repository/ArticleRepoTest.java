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
@AdditionalClasses({ArticleRepo.class, TestPersistenceFactory.class})
public class ArticleRepoTest {
    @EJB
    private ArticleRepo articleRepository;

    @After
    public void after() {
        for (Article a : articleRepository.getAll()) {
            articleRepository.delete(a);
        }
    }

    @Test
    public void getById() throws Exception {
        Article article = new Article();
        article.setAmount(BigDecimal.TEN);
        article.setType(Article.Type.INCOME);
        article.setDescription("text");
        LocalDateTime date = LocalDateTime.of(2018, 10, 10, 10, 10, 10);
        article.setCreateOn(date);

        articleRepository.save(article);

        long id = article.getId();
        assertThat(articleRepository.getById(id)).isEqualTo(article);
    }

    @Test
    public void save() throws Exception {
        Article article = new Article();
        article.setAmount(BigDecimal.TEN);
        article.setType(Article.Type.INCOME);
        article.setDescription("text");
        LocalDateTime date = LocalDateTime.now();
        article.setCreateOn(date);

        assertThat(article.getId() == 0).isTrue();

        articleRepository.save(article);

        assertThat(article.getId() > 0).isTrue();
    }

    @Test
    public void delete() throws Exception {
        Article article = new Article();
        article.setAmount(BigDecimal.TEN);
        article.setType(Article.Type.INCOME);
        article.setDescription("text");
        LocalDateTime date = LocalDateTime.now();
        article.setCreateOn(date);

        articleRepository.save(article);

        long id = article.getId();
        assertThat(articleRepository.getById(id)).isNotNull();
        assertThat(articleRepository.delete(article)).isTrue();
        assertThat(articleRepository.getById(id)).isNull();
    }

    @Test
    public void update() throws Exception {
        Article article = new Article();
        article.setAmount(BigDecimal.TEN);
        article.setType(Article.Type.INCOME);
        article.setDescription("text");
        LocalDateTime date = LocalDateTime.now();
        article.setCreateOn(date);

        articleRepository.save(article);

        long id = article.getId();
        String newText = "newtext";
        article.setDescription(newText);

        articleRepository.save(article);

        article = articleRepository.getById(id);
        assertThat(article.getDescription()).isEqualTo(newText);
    }

    @Test
    public void getAll() {
        Article article = new Article();
        article.setAmount(BigDecimal.TEN);
        article.setType(Article.Type.INCOME);
        article.setDescription("text");
        LocalDateTime date = LocalDateTime.now();
        article.setCreateOn(date);

        Article article1 = new Article();
        article1.setAmount(BigDecimal.ZERO);
        article1.setType(Article.Type.CONSUMPTION);
        article1.setDescription("text");
        LocalDateTime date1 = LocalDateTime.now();
        article1.setCreateOn(date1);

        articleRepository.save(article);
        articleRepository.save(article1);

        assertThat(articleRepository.getAll().size() == 2).isTrue();
    }

}