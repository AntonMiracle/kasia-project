package com.kasia.service.imp;

import com.kasia.model.Article;
import com.kasia.repository.ArticleRepository;
import com.kasia.service.ArticleService;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class ArticleServiceImp implements ArticleService {
    @EJB
    private ArticleRepository articleRepository;

    @Override
    public Article create(String description, Article.Type type, BigDecimal amount) throws ValidationException {
        Article article = new Article(type, amount, LocalDateTime.now().withNano(0));
        article.setDescription(description);
        if (!isValid(article)) throw new ValidationException();
        articleRepository.save(article);
        return articleRepository.getById(article.getId());
    }


    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        Article article = articleRepository.getById(id);
        if (article == null) return true;
        return articleRepository.delete(article);
    }

    @Override
    public Article update(Article article) throws ValidationException, IllegalArgumentException {
        if (!isValid(article)) throw new ValidationException();
        if (article.getId() == 0) throw new IllegalArgumentException();
        articleRepository.save(article);
        return articleRepository.getById(article.getId());
    }

    @Override
    public Article getArticleById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return articleRepository.getById(id);
    }

    @Override
    public Set<Article> getAllArticles() {
        return articleRepository.getAll();
    }

    @Override
    public boolean isValid(Article model) {
        if (model == null) throw new NullPointerException();
        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }
}
