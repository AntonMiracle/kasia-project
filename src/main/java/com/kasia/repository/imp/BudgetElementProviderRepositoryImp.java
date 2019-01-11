package com.kasia.repository.imp;

import com.kasia.model.BudgetElementProvider;
import com.kasia.repository.BudgetElementProviderRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class BudgetElementProviderRepositoryImp implements BudgetElementProviderRepository {
    @Inject
    private EntityManager em;

    @Override
    public void save(BudgetElementProvider model) {
        if (em.contains(model)) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public BudgetElementProvider getById(long id) {
        return em.find(BudgetElementProvider.class, id);
    }

    @Override
    public void delete(BudgetElementProvider model) {
        em.remove(model);
    }

    @Override
    public Set<BudgetElementProvider> getAll() {
        Query query = em.createQuery("SELECT bep FROM BudgetElementProvider bep");
        return new HashSet<>(query.getResultList());
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
