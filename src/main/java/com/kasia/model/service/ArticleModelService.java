package com.kasia.model.service;

import com.kasia.exception.OnUseRunTimeException;
import com.kasia.model.Article;

import javax.validation.ValidationException;
import java.util.Set;

public interface ArticleModelService extends ModelService {

    Article create(String name, Article.Type type) throws ValidationException;

    boolean delete(long id) throws IllegalArgumentException, OnUseRunTimeException;

    Article update(Article article) throws ValidationException, IllegalArgumentException;

    Article getArticleById(long id) throws IllegalArgumentException;

    Set<Article> getAllArticles();
}
