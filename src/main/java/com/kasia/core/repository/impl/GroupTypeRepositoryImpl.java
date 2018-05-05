package com.kasia.core.repository.impl;

import com.kasia.core.model.GroupType;
import com.kasia.core.repository.GroupTypeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class GroupTypeRepositoryImpl implements GroupTypeRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public GroupType saveOrUpdate(GroupType model) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(model);
        session.flush();
        return model;
    }

    @Override
    public GroupType get(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from GroupType as groupType where groupType.id = '" + id.toString() + "'");
        return (GroupType) query.uniqueResult();
    }

    @Override
    public Boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(get(id));
        session.flush();
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<GroupType> get() {
        Session session = sessionFactory.getCurrentSession();
        Set<GroupType> groupTypes = (Set<GroupType>) session
                .createQuery("from GroupType")
                .getResultStream()
                .collect(Collectors.toCollection(HashSet<GroupType>::new));
        return groupTypes;
    }

    @Override
    public GroupType get(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from GroupType as groupType where groupType.name = '" + name + "'");
        return (GroupType) query.uniqueResult();
    }
}
