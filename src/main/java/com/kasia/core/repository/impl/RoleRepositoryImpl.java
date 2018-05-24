package com.kasia.core.repository.impl;

import com.kasia.core.model.GroupType;
import com.kasia.core.model.Role;
import com.kasia.core.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Role saveOrUpdate(Role model) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(model);
        session.flush();
        return model;
    }

    @Override
    public Role get(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Role as role where role.id = '" + id.toString() + "'");
        return (Role) query.uniqueResult();
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(get(id));
        session.flush();
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Role> get() {
        Session session = sessionFactory.getCurrentSession();
        Set<Role> groupTypes = (Set<Role>) session
                .createQuery("from Role")
                .getResultStream()
                .collect(Collectors.toCollection(HashSet<Role>::new));
        return groupTypes;
    }

    @Override
    public Role get(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Role as role where role.name = '" + name + "'");
        return (Role) query.uniqueResult();
    }

    @Override
    public boolean isNameExist(String name) {
        return get(name) != null;
    }
}
