package com.kasia.service.imp;

import com.kasia.exception.OnUseRunTimeException;
import com.kasia.model.Article;
import com.kasia.repository.ArticleRepository;
import com.kasia.service.ArticleService;
import com.kasia.service.OperationService;
import com.kasia.service.ValidationService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.ValidationException;
import java.util.Set;

public class ArticleServiceImp implements ArticleService{
    @EJB
    private ArticleRepository articleRepository;
    @Inject
    private OperationService operationService;
    @Inject
    private ValidationService<Article> validationService;

    @Override
    public Article create(String name, Article.Type type) throws ValidationException {
        Article article = new Article(name, type);
        article.setDescription("");
        if (!validationService.isValid(article)) throw new ValidationException();
        articleRepository.save(article);
        return articleRepository.getById(article.getId());
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException, OnUseRunTimeException {
        if (id <= 0) throw new IllegalArgumentException();
        Article article = articleRepository.getById(id);
        if (article == null) return true;
        if (operationService.getOperationsByArticleId(article.getId()).size() != 0) throw new OnUseRunTimeException();
        return articleRepository.delete(article);
    }

    @Override
    public Article update(Article article) throws ValidationException, IllegalArgumentException {
        if (!validationService.isValid(article)) throw new ValidationException();
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
}
