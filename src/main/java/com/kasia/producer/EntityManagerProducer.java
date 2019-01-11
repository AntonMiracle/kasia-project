package com.kasia.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {
    @PersistenceContext(unitName = "UnitForTest")
    private EntityManagerFactory emf;

    @Produces
    public EntityManager entityManager() {
        return emf.createEntityManager();
    }
}
