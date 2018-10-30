package com.kasia.repository;

import com.kasia.model.Economy;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class EconomyRepository implements Repository<Economy> {
    @PersistenceContext(unitName = PERSISTENT_UNIT_NAME)
    private EntityManager entityManager;

    @Override
    public Economy getById(long id) {
        return entityManager.find(Economy.class, id);
    }

    @Override
    public Set<Economy> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Economy e ");
        return new HashSet<>(query.getResultList());
    }

    @Override
    public boolean delete(Economy model) {
        model = entityManager.contains(model) ? model : entityManager.merge(model);
        entityManager.remove(model);
        return true;
    }

    @Override
    public Economy save(Economy model) {
        if (model.getId() > 0) {
            model = entityManager.merge(model);
        } else {
            entityManager.persist(model);
        }
        return model;
    }
}
