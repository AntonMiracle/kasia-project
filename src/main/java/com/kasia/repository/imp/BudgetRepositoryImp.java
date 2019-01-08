package com.kasia.repository.imp;

import com.kasia.model.Budget;
import com.kasia.repository.BudgetRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class BudgetRepositoryImp implements BudgetRepository {

    @PersistenceContext(unitName = "UnitForTest")
    private EntityManager em;

    @Override
    public void save(Budget budget) {
        if (em.contains(budget)) {
            em.merge(budget);
        } else {
            em.persist(budget);
        }
    }

    @Override
    public Budget getById(long id) {
        return em.find(Budget.class, id);
    }

    @Override
    public void delete(Budget budget) {
        em.remove(budget);
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
