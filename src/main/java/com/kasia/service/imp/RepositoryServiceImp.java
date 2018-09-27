package com.kasia.service.imp;

import com.kasia.service.RepositoryService;

import javax.persistence.*;

public class RepositoryServiceImp implements RepositoryService {
    private final String UNIT_NAME;
    private static EntityManagerFactory factory;

    public RepositoryServiceImp() {
        this.UNIT_NAME = "db-unit";
    }

    public RepositoryServiceImp(String UNIT_NAME) {
        this.UNIT_NAME = UNIT_NAME;
    }

    private synchronized EntityManagerFactory initializedFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(UNIT_NAME);
        }
        return factory;
    }

    @Override
    public EntityManager getManager() throws PersistenceException {
        if (initializedFactory() == null) throw new PersistenceException();
        return factory.createEntityManager();
    }

    @Override
    public void closeFactory() {
        if (factory != null && factory.isOpen()) factory.close();
    }

}
