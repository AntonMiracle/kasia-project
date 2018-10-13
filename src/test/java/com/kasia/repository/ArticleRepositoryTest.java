package com.kasia.repository;

import com.kasia.model.Article;
import com.kasia.repository.imp.ArticleRepositoryImp;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArticleRepositoryTest extends RepositoryTestHelper {
    private ArticleRepository articleRepository;
    private Article article;

    @Before
    public void before() {
        articleRepository = new ArticleRepositoryImp(repositoryConnectionService.getEntityManager());
        article = new Article();
    }

    @Test
    public void getById() throws Exception {
        article.setAmount(BigDecimal.TEN);
        article.setType(Article.Type.INCOME);
        article.setDescription("text");
        LocalDateTime date = LocalDateTime.now();
        article.setCreateOn(date);

        articleRepository.save(article);

        long id = article.getId();

        assertThat(articleRepository.getById(id)).isEqualTo(article);
    }

    @Test
    public void delete() throws Exception {
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
        article.setAmount(BigDecimal.TEN);
        article.setType(Article.Type.INCOME);
        article.setDescription("text");
        LocalDateTime date = LocalDateTime.now();
        article.setCreateOn(date);

        articleRepository.save(article);

        long id = article.getId();
        String newText = "newtext";
        article.setDescription(newText);

        articleRepository.update(article);

        article = articleRepository.getById(id);
        assertThat(article.getDescription()).isEqualTo(newText);
    }

    @Test
    public void save() throws Exception {
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
    public void getAll() {
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
        article1.setCreateOn(date);


        articleRepository.save(article);
        articleRepository.save(article1);

        assertThat(articleRepository.getAll().size() == 2).isTrue();
    }
}