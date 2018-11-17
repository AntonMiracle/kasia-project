package com.kasia.model.repository.imp;

import com.kasia.model.Budget;
import com.kasia.model.repository.BudgetRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class BudgetRepositoryImp implements BudgetRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public Budget getById(long id) {
        return entityManager.find(Budget.class, id);
    }

    @Override
    public Set<Budget> getAll() {
        Query query = entityManager.createQuery("SELECT b FROM Budget b ");
        return new HashSet<>(query.getResultList());
    }

    @Override
    public boolean delete(Budget model) {
        model = entityManager.contains(model) ? model : entityManager.merge(model);
        entityManager.remove(model);
        return true;
    }

    @Override
    public Budget save(Budget model) {
        if (model.getId() > 0) {
            entityManager.merge(model);
        } else {
            entityManager.persist(model);
        }
        return model;
    }
}
