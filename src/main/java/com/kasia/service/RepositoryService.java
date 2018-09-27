package com.kasia.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

public interface RepositoryService {
    EntityManager getManager() throws PersistenceException;

    void closeFactory();
}
