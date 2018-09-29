package com.kasia.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

public interface RepositoryConnectionService {
    EntityManager getEntityManager() throws PersistenceException;

    void closeEntityManagerFactory();
}
