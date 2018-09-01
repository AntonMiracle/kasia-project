package com.kasia.repository;

import com.kasia.model.Article;

import java.util.Set;

public interface ArticleRepository {
    Article getById(long id);

    Set<Article> getAll();

    boolean delete(Article article);

    boolean update(Article article);

    Article save(Article article);
}
