package com.kasia.service;

import com.kasia.exception.OnUseRunTimeException;
import com.kasia.model.Article;

import javax.validation.ValidationException;
import java.util.Set;

public interface ArticleService extends ValidationService<Article>,Service {

    Article create(String name, Article.Type type) throws ValidationException;

    boolean delete(long id) throws IllegalArgumentException, OnUseRunTimeException;

    Article update(Article article) throws ValidationException, IllegalArgumentException;

    Article getArticleById(long id) throws IllegalArgumentException;

    Set<Article> getAllArticles();
}
