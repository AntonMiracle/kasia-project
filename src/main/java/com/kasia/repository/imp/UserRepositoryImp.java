package com.kasia.repository.imp;

import com.kasia.model.User;
import com.kasia.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class UserRepositoryImp implements UserRepository {
    private EntityManager entityManager;

    public UserRepositoryImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserRepositoryImp() {
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email='" + email + "'");User user = null;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException ex) {

        }
        return user;
    }

    @Override
    public User getByNick(String nick) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.nick='" + nick + "'");
        User user = null;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException ex) {

        }
        return user;
    }

    @Override
    public boolean delete(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public User save(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(ex);
        }
        return user;
    }
}
