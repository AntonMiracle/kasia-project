package com.kasia.repository.imp;

import com.kasia.model.UserBudget;
import com.kasia.repository.UserBudgetRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class UserBudgetRepositoryImp implements UserBudgetRepository {
    @Inject
    private EntityManager em;

    @Override
    public void save(UserBudget model) {
        if (em.contains(model)) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public UserBudget getById(long id) {
        return em.find(UserBudget.class, id);
    }

    @Override
    public void delete(UserBudget model) {
        em.remove(model);
    }

    @Override
    public Set<UserBudget> getAll() {
        Query query = em.createQuery("SELECT ub FROM UserBudget ub");
        return new HashSet<>(query.getResultList());
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
