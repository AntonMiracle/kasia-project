package com.kasia.service.model;

import com.kasia.exception.OnUseRunTimeException;
import com.kasia.model.Article;
import com.kasia.service.Service;

import javax.validation.ValidationException;
import java.util.Set;

public interface ArticleService extends Service {

    Article create(String name, Article.Type type) throws ValidationException;

    boolean delete(long id) throws IllegalArgumentException, OnUseRunTimeException;

    Article update(Article article) throws ValidationException, IllegalArgumentException;

    Article getArticleById(long id) throws IllegalArgumentException;

    Set<Article> getAllArticles();
}
