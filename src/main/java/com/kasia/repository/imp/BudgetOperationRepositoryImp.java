package com.kasia.repository.imp;

import com.kasia.model.BudgetOperation;
import com.kasia.repository.BudgetOperationRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class BudgetOperationRepositoryImp implements BudgetOperationRepository {
    @Inject
    private EntityManager em;


    @Override
    public void save(BudgetOperation model) {
        if (em.contains(model)) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public BudgetOperation getById(long id) {
        return em.find(BudgetOperation.class, id);
    }

    @Override
    public void delete(BudgetOperation model) {
        em.remove(model);
    }

    @Override
    public Set<BudgetOperation> getAll() {
        Query query = em.createQuery("SELECT bo FROM BudgetOperation bo");
        return new HashSet<>(query.getResultList());
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
