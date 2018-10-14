package com.kasia.service;

import com.kasia.model.Article;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Set;

public interface ArticleService extends ValidationService<Article> {

    Article create(String description, Article.Type type, BigDecimal amount) throws ValidationException;

    boolean delete(long id) throws IllegalArgumentException;

    boolean update(Article article) throws ValidationException, IllegalArgumentException;

    Article getArticleById(long id) throws IllegalArgumentException;

    Set<Article> getAllArticles();
}
