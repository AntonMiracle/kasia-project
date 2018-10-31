package com.kasia.repository;

import com.kasia.model.Employer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class EmployerRepository implements Repository<Employer> {
    @PersistenceContext(unitName = PERSISTENT_UNIT_NAME)
    private EntityManager entityManager;

    @Override
    public Employer getById(long id) {
        return entityManager.find(Employer.class, id);
    }

    @Override
    public Set<Employer> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Employer e ");
        return new HashSet<>(query.getResultList());
    }

    @Override
    public boolean delete(Employer model) {
        model = entityManager.contains(model) ? model : entityManager.merge(model);
        entityManager.remove(model);
        return true;
    }

    @Override
    public Employer save(Employer model) {
        if (model.getId() > 0) {
            entityManager.merge(model);
        } else {
            entityManager.persist(model);
        }
        return model;
    }

    public Employer getByName(String name) {
        Query query = entityManager.createQuery("SELECT e FROM Employer e WHERE e.name='" + name + "'");
        try {
            return (Employer) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
