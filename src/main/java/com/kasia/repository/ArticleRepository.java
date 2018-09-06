package com.kasia.repository;

import com.kasia.model.Article;

public interface ArticleRepository {
    Article getById(long id);

    boolean delete(Article article);

    boolean update(Article article);

    Article save(Article article);
}
