package com.kasia.repository.imp;

import com.kasia.model.Article;
import com.kasia.repository.ArticleRepository;

import javax.persistence.EntityManager;

public class ArticleRepositoryImp implements ArticleRepository {
    private EntityManager entityManager;

    public ArticleRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ArticleRepositoryImp() {
    }

    @Override
    public Article getById(long id) {
        return entityManager.find(Article.class, id);
    }

    @Override
    public boolean delete(Article article) {
        try {
            entityManager.getTransaction().begin();
            article = entityManager.contains(article) ? article : entityManager.merge(article);
            entityManager.remove(article);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public boolean update(Article article) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(article);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public Article save(Article article) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(article);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return article;
    }
}
