package com.kasia.service.imp;

import com.kasia.model.Article;
import com.kasia.repository.ArticleRepository;
import com.kasia.service.ArticleService;

import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ArticleServiceImp implements ArticleService {
    private ArticleRepository articleRepository;

    public ArticleServiceImp(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleServiceImp() {
    }

    public ArticleRepository getArticleRepository() {
        return articleRepository;
    }

    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article create(String description, Article.Type type, BigDecimal amount) throws ValidationException {
        Article article = new Article();
        article.setDescription(description);
        article.setCreateOn(LocalDateTime.now());
        article.setType(type);
        article.setAmount(amount);

        if (!isValid(article)) throw new ValidationException();

        return articleRepository.save(article);
    }


    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        Article article = getArticleById(id);
        if (article == null) return true;
        return articleRepository.delete(article);
    }

    @Override
    public boolean update(Article article) throws ValidationException, IllegalArgumentException {
        if (!isValid(article)) throw new ValidationException();
        if (article.getId() == 0) throw new IllegalArgumentException();
        return articleRepository.update(article);
    }

    @Override
    public Article getArticleById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return articleRepository.getById(id);
    }

    @Override
    public Set<Article> getArticlesByType(Set<Article> articles, Article.Type type) throws NullPointerException {
        if (articles == null || type == null) throw new NullPointerException();
        Set<Article> result = new HashSet<>();
        for (Article article : articles) {
            if (article.getType() == type) {
                result.add(article);
            }
        }
        return result;
    }

    @Override
    public boolean isValid(Article model) {
        if (model == null) throw new NullPointerException();
        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }
}
