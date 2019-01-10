package com.kasia.producer;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {
    @PersistenceContext(unitName = "UnitForTest")
    private EntityManager em;

    @Produces
    @RequestScoped
    public EntityManager entityManager() {
        return em;
    }
}
