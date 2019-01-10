package com.kasia.repository.imp;

import com.kasia.model.Element;
import com.kasia.repository.ElementRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class ElementRepositoryImp implements ElementRepository {
    @Inject
    private EntityManager em;

    @Override
    public void save(Element model) {
        if (em.contains(model)) {
            em.merge(model);
        } else {
            em.persist(model);
        }
    }

    @Override
    public Element getById(long id) {
        return em.find(Element.class, id);
    }

    @Override
    public void delete(Element model) {
        em.remove(model);
    }

    @Override
    public Set<Element> getAll() {
        Query query = em.createQuery("SELECT e FROM Element e");
        return new HashSet<>(query.getResultList());
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
