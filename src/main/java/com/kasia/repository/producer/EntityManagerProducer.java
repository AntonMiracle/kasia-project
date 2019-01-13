package com.kasia.repository.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {
    @PersistenceContext(unitName = "testUnit")
    private EntityManager em;

    @Produces
    public EntityManager entityManager() {
        return em;
    }
}
