package com.kasia.service.imp;

import com.kasia.model.Article;
import com.kasia.repository.ArticleRepository;
import com.kasia.service.ArticleService;

import javax.validation.ValidatorFactory;
import java.util.Set;

public class ArticleServiceImp implements ArticleService {
    private ArticleRepository repository;

    @Override
    public Article create(Article article) {
        if (!isValid(article)) return null;

        return repository.save(article);
    }

    @Override
    public boolean delete(Article article) {
        return false;
    }

    @Override
    public boolean update(Article article) {
        return false;
    }

    @Override
    public Article getArticleById(long id) {
        return null;
    }

    @Override
    public Set<Article> getArticlesByType(Set<Article> articles, Article.Type type) {
        return null;
    }

    @Override
    public boolean isValid(Article model) {
        if (model == null || !(model instanceof Article)) return false;

        boolean isValid = false;

        try (ValidatorFactory factory = getValidatorFactory()) {
            isValid = factory.getValidator().validate(model).size() == 0;
        }

        return isValid;
    }
}
