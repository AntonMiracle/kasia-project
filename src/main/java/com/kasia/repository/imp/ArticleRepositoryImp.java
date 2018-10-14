package com.kasia.repository.imp;

import com.kasia.model.Article;
import com.kasia.repository.ArticleRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

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

    @Override
    public Set<Article> getAll() {
        Query query = entityManager.createQuery("SELECT a FROM Article a ");
        return new HashSet<>(query.getResultList());
    }

}
