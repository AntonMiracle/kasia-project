package com.kasia.repository.imp;

import com.kasia.model.BudgetElement;
import com.kasia.repository.BudgetElementRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class BudgetElementRepositoryImp implements BudgetElementRepository {
    @Inject
    private EntityManager em;

    @Override
    public void save(BudgetElement model) {
        if (em.contains(model)) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public BudgetElement getById(long id) {
        return em.find(BudgetElement.class, id);
    }

    @Override
    public void delete(BudgetElement model) {
        em.remove(model);
    }

    @Override
    public Set<BudgetElement> getAll() {
        Query query = em.createQuery("SELECT be FROM BudgetElement be");
        return new HashSet<>(query.getResultList());
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
