package com.kasia.repository.imp;

import com.kasia.model.Economy;
import com.kasia.repository.EconomyRepository;

import javax.persistence.EntityManager;

public class EconomyRepositoryImp implements EconomyRepository {
    private EntityManager entityManager;

    public EconomyRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EconomyRepositoryImp() {
    }

    @Override
    public Economy getById(long id) {
        return entityManager.find(Economy.class, id);
    }

    @Override
    public boolean delete(Economy economy) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(economy);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public boolean update(Economy economy) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(economy);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public Economy save(Economy economy) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(economy);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return economy;
    }
}
