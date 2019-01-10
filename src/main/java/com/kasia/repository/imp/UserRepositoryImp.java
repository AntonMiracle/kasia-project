package com.kasia.repository.imp;

import com.kasia.model.User;
import com.kasia.repository.UserRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class UserRepositoryImp implements UserRepository {
    @Inject
    private EntityManager em;

    @Override
    public void save(User model) {
        if (em.contains(model)) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public User getById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public void delete(User model) {
        em.remove(model);
    }

    @Override
    public Set<User> getAll() {
        Query query = em.createQuery("SELECT u FROM User u");
        return new HashSet<>(query.getResultList());

    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public User getByEmail(String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email='" + email + "'");
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User getByName(String name) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.name='" + name + "'");
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
