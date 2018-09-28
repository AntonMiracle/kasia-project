package com.kasia.repository.imp;

import com.kasia.model.Article;
import com.kasia.repository.ArticleRepository;
import com.kasia.service.ArticleService;
import com.kasia.service.RepositoryService;
import com.kasia.service.imp.ArticleServiceImp;
import com.kasia.service.imp.RepositoryServiceImp;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class ArticleRepositoryImp implements ArticleRepository {
    RepositoryService repository;

    public ArticleRepositoryImp(RepositoryService repository) {
        this.repository = repository;
    }

    public ArticleRepositoryImp() {
        repository = new RepositoryServiceImp();
    }

    @Override
    public Article getById(long id) {
        return repository.getManager().find(Article.class, id);
    }

    @Override
    public boolean delete(Article article) {
        EntityManager em = repository.getManager();
        try {
            em.getTransaction().begin();
            article = em.contains(article)? article : em.merge(article);
            em.remove(article);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public boolean update(Article article) {
        EntityManager em = repository.getManager();
        try {
            em.getTransaction().begin();
            em.merge(article);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public Article save(Article article) {
        EntityManager em = repository.getManager();
        try {
            em.getTransaction().begin();
            em.persist(article);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return article;
    }

    public static void main(String[] args) {
        RepositoryService repository = new RepositoryServiceImp();
        ArticleRepository articleRepository = new ArticleRepositoryImp(repository);
        assert repository != null;

        ArticleService service = new ArticleServiceImp(articleRepository);
        Article article = service.create("description", Article.Type.INCOME, BigDecimal.TEN);
        System.out.println(article);
        for (int i = 1; i < 3; i++) {
            System.out.println(service.getArticleById(i));
        }

        repository.closeFactory();

    }
}
