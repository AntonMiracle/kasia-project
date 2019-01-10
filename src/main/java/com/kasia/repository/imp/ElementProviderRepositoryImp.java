package com.kasia.repository.imp;

import com.kasia.model.ElementProvider;
import com.kasia.repository.ElementProviderRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class ElementProviderRepositoryImp implements ElementProviderRepository {
    @Inject
    private EntityManager em;

    @Override
    public void save(ElementProvider model) {
        if (em.contains(model)) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public ElementProvider getById(long id) {
        return em.find(ElementProvider.class, id);
    }

    @Override
    public void delete(ElementProvider model) {
        em.remove(model);
    }

    @Override
    public Set<ElementProvider> getAll() {
        Query query = em.createQuery("SELECT ep FROM ElementProvider ep");
        return new HashSet<>(query.getResultList());
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
