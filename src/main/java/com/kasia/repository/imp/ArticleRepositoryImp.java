package com.kasia.repository.imp;

import com.kasia.model.Article;
import com.kasia.repository.ArticleRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class ArticleRepositoryImp implements ArticleRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public Article getById(long id) {
        return entityManager.find(Article.class, id);
    }

    @Override
    public Set<Article> getAll() {
        Query query = entityManager.createQuery("SELECT a FROM Article a ");
        return new HashSet<>(query.getResultList());
    }

    @Override
    public boolean delete(Article model) {
        model = entityManager.contains(model) ? model : entityManager.merge(model);
        entityManager.remove(model);
        return true;
    }

    @Override
    public Article save(Article model) {
        if (model.getId() > 0) {
            entityManager.merge(model);
        } else {
            entityManager.persist(model);
        }
        return model;
    }
}
