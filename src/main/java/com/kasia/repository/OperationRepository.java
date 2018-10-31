package com.kasia.repository;

import com.kasia.model.Operation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class OperationRepository implements Repository<Operation> {

    @PersistenceContext(unitName = PERSISTENT_UNIT_NAME)
    private EntityManager entityManager;

    @Override
    public Operation getById(long id) {
        return entityManager.find(Operation.class, id);
    }

    @Override
    public Set<Operation> getAll() {
        Query query = entityManager.createQuery("SELECT o FROM Operation o ");
        return new HashSet<>(query.getResultList());
    }

    @Override
    public boolean delete(Operation model) {
        model = entityManager.contains(model) ? model : entityManager.merge(model);
        entityManager.remove(model);
        return true;
    }

    @Override
    public Operation save(Operation model) {
        if (model.getId() > 0) {
            entityManager.merge(model);
        } else {
            entityManager.persist(model);
        }
        return model;
    }

    public Set<Operation> getByUserId(long id) {
        Query query = entityManager.createQuery("SELECT o FROM Operation o WHERE o.user.id=" + id);
        return new HashSet<>(query.getResultList());
    }

    public Set<Operation> getByEmployerId(long id) {
        Query query = entityManager.createQuery("SELECT o FROM Operation o WHERE o.employer.id=" + id);
        return new HashSet<>(query.getResultList());
    }

    public Set<Operation> getByArticleId(long id) {
        Query query = entityManager.createQuery("SELECT o FROM Operation o WHERE o.article.id=" + id);
        return new HashSet<>(query.getResultList());
    }


}
