package com.kasia.model.service.imp;

import com.kasia.exception.OnUseRunTimeException;
import com.kasia.model.Article;
import com.kasia.repository.ArticleRepository;
import com.kasia.model.service.ArticleModelService;
import com.kasia.model.service.OperationModelService;
import com.kasia.validation.ValidationService;

import javax.inject.Inject;
import javax.validation.ValidationException;
import java.util.Set;

public class ArticleModelServiceImp implements ArticleModelService {
    @Inject
    private ArticleRepository repository;
    @Inject
    private OperationModelService operationService;
    @Inject
    private ValidationService validationService;

    @Override
    public Article create(String name, Article.Type type) throws ValidationException {
        Article article = new Article(name, "", type);
        article.setDescription("");
        if (!validationService.isValid(article)) throw new ValidationException();
        repository.save(article);
        return repository.getById(article.getId());
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException, OnUseRunTimeException {
        if (id <= 0) throw new IllegalArgumentException();
        Article article = repository.getById(id);
        if (article == null) return true;
        if (operationService.getOperationsByArticleId(article.getId()).size() != 0) throw new OnUseRunTimeException();
        return repository.delete(article);
    }

    @Override
    public Article update(Article article) throws ValidationException, IllegalArgumentException {
        if (!validationService.isValid(article)) throw new ValidationException();
        if (article.getId() == 0) throw new IllegalArgumentException();
        repository.save(article);
        return repository.getById(article.getId());
    }

    @Override
    public Article getArticleById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return repository.getById(id);
    }

    @Override
    public Set<Article> getAllArticles() {
        return repository.getAll();
    }
}
