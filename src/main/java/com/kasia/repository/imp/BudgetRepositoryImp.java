package com.kasia.repository.imp;

import com.kasia.model.Budget;
import com.kasia.repository.BudgetRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class BudgetRepositoryImp implements BudgetRepository {
    @Inject
    private EntityManager em;

    @Override
    public void save(Budget model) {
        if (em.contains(model)) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public Budget getById(long id) {
        return em.find(Budget.class, id);
    }

    @Override
    public void delete(Budget model) {
        em.remove(model);
    }

    @Override
    public Set<Budget> getAll() {
        Query query = em.createQuery("SELECT b FROM Budget b");
        return new HashSet<>(query.getResultList());
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
