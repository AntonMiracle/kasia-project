package com.kasia.service.imp;

import com.kasia.service.RepositoryConnectionService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class RepositoryConnectionServiceImp implements RepositoryConnectionService {
    private final String UNIT_NAME;
    private static EntityManagerFactory factory;

    public RepositoryConnectionServiceImp() {
        this.UNIT_NAME = "db-unit";
    }

    public RepositoryConnectionServiceImp(String UNIT_NAME) {
        this.UNIT_NAME = UNIT_NAME;
    }

    private synchronized EntityManagerFactory initializedFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(UNIT_NAME);
        }
        return factory;
    }

    @Override
    public EntityManager getEntityManager() throws PersistenceException {
        if (initializedFactory() == null) throw new PersistenceException();
        return factory.createEntityManager();
    }

    @Override
    public void closeEntityManagerFactory() {
        if (factory != null && factory.isOpen()) factory.close();
    }

}
