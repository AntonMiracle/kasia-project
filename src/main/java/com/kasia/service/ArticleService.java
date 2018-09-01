package com.kasia.service;

import com.kasia.model.Article;

import java.util.Set;

public interface ArticleService extends ValidationService<Article> {
    Article create(Article article);

    boolean delete(Article article);

    boolean update(Article article);

    Article getArticleById(long id);

    Set<Article> getArticlesByType(Set<Article> articles, Article.Type type);

}
