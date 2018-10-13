package com.kasia.repository.imp;

import com.kasia.model.Budget;
import com.kasia.repository.BudgetRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

public class BudgetRepositoryImp implements BudgetRepository {
    private EntityManager entityManager;

    public BudgetRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public BudgetRepositoryImp() {

    }

    @Override
    public Budget getById(long id) {
        return entityManager.find(Budget.class, id);
    }

    @Override
    public boolean delete(Budget budget) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(budget);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public boolean update(Budget budget) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(budget);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public Budget save(Budget budget) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(budget);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return budget;
    }

    @Override
    public Set<Budget> getAll() {
        Query query = entityManager.createQuery("SELECT b FROM Budget b ");
        return new HashSet<>(query.getResultList());
    }
}
