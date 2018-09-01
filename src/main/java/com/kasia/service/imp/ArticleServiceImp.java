package com.kasia.service.imp;

import com.kasia.model.Article;
import com.kasia.service.ArticleService;

import java.util.Set;

public class ArticleServiceImp implements ArticleService {
    @Override
    public Article create(Article article) {
        return null;
    }

    @Override
    public boolean delete(Article article) {
        return false;
    }

    @Override
    public boolean update(Article article) {
        return false;
    }

    @Override
    public Article getArticleById(long id) {
        return null;
    }

    @Override
    public Set<Article> getArticlesByType(Set<Article> articles, Article.Type type) {
        return null;
    }
}
