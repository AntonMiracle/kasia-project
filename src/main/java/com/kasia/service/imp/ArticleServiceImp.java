package com.kasia.service.imp;

import com.kasia.model.Article;
import com.kasia.repository.ArticleRepository;
import com.kasia.service.ArticleService;

import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ArticleServiceImp implements ArticleService {
    private ArticleRepository repository;

    @Override
    public Article create(Article article) {
        if (article == null || !isValid(article)) return null;
        article.setCreateOn(LocalDateTime.now());
        return repository.save(article);
    }

    @Override
    public boolean delete(Article article) {
        if (article == null || !isValid(article) || article.getId() <= 0) return false;
        return repository.delete(article);
    }

    @Override
    public boolean update(Article article) {
        if (article == null || !isValid(article) || article.getId() <= 0) return false;
        return repository.update(article);
    }

    @Override
    public Article getArticleById(long id) {
        if (id <= 0) return null;
        return repository.getById(id);
    }

    @Override
    public Set<Article> getArticlesByType(Set<Article> articles, Article.Type type) {
        if (articles == null || type == null) return null;
        Set<Article> result = new HashSet<>();
        for (Article article : articles) {
            if (article.getType().equals(type)) {
                result.add(article);
            }
        }
        return result;
    }

    @Override
    public boolean isValid(Article model) {
        if (model == null) return false;
        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }
}
