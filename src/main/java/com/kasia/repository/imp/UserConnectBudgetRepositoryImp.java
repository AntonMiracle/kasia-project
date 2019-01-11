package com.kasia.repository.imp;

import com.kasia.model.UserConnectBudget;
import com.kasia.repository.UserConnectBudgetRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class UserConnectBudgetRepositoryImp implements UserConnectBudgetRepository {
    @Inject
    private EntityManager em;


    @Override
    public void save(UserConnectBudget model) {
        if (em.contains(model)) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public UserConnectBudget getById(long id) {
        return em.find(UserConnectBudget.class, id);
    }

    @Override
    public void delete(UserConnectBudget model) {
        em.remove(model);
    }

    @Override
    public Set<UserConnectBudget> getAll() {
        Query query = em.createQuery("SELECT ucb FROM UserConnectBudget ucb");
        return new HashSet<>(query.getResultList());
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
