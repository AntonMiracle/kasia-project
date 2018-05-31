package com.kasia.core.repository.impl;

import com.kasia.core.model.Role;
import com.kasia.core.model.User;
import com.kasia.core.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User saveOrUpdate(User model) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(model);
        session.flush();
        return model;
    }

    @Override
    public User get(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User as user where user.id = '" + id.toString() + "'");
        return (User) query.uniqueResult();
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(get(id));
        session.flush();
        return true;
    }

    @Override
    public Set<User> get() {
        Session session = sessionFactory.getCurrentSession();
        Set<User> users = (Set<User>) session
                .createQuery("from User")
                .getResultStream()
                .collect(Collectors.toCollection(HashSet<User>::new));
        return users;
    }

    @Override
    public User get(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User as user where user.username = '" + username + "'");
        return (User) query.uniqueResult();
    }

    @Override
    public boolean isUsernameExist(String username) {
        return get(username) != null;
    }
}
