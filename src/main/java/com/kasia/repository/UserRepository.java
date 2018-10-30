package com.kasia.repository;

import com.kasia.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class UserRepository implements Repository<User> {

    @PersistenceContext(unitName = PERSISTENT_UNIT_NAME)
    private EntityManager entityManager;

    @Override
    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public Set<User> getAll() {
        Query query = entityManager.createQuery("SELECT u FROM User u ");
        return new HashSet<>(query.getResultList());
    }

    @Override
    public boolean delete(User model) {
        model = entityManager.contains(model) ? model : entityManager.merge(model);
        entityManager.remove(model);
        return true;
    }

    @Override
    public User save(User model) {
        if (model.getId() > 0) {
            entityManager.merge(model);
        } else {
            entityManager.persist(model);
        }
        return model;
    }

    public User getByEmail(String email) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email='" + email + "'");
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public User getByNick(String nick) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.nick='" + nick + "'");
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
