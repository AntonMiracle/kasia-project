package com.kasia.repository.imp;

import com.kasia.model.Operation;
import com.kasia.repository.OperationRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class OperationRepositoryImp implements OperationRepository {
    @Inject
    private EntityManager em;

    @Override
    public void save(Operation model) {
        if (em.contains(model)) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public Operation getById(long id) {
        return em.find(Operation.class, id);
    }

    @Override
    public void delete(Operation model) {
        em.remove(model);
    }

    @Override
    public Set<Operation> getAll() {
        Query query = em.createQuery("SELECT o FROM Operation o");
        return new HashSet<>(query.getResultList());
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
