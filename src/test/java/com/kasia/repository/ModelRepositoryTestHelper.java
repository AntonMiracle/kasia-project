package com.kasia.repository;

import com.kasia.model.*;
import com.kasia.repository.imp.*;
import org.junit.After;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ModelRepositoryTestHelper {
    protected final static String PERSISTENCE_TEST_UNIT_NAME = "testUnit";
    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_TEST_UNIT_NAME);
    protected EntityManager em;
    protected EntityTransaction et;

    @After
    public void tearDown() {
        if (em != null) em.close();
    }

    protected void persistElement(Element element, EntityManager em) {
        ElementRepositoryImp er = new ElementRepositoryImp();
        er.setEm(em);
        er.save(element);
    }

    protected void persistElementProvider(ElementProvider elementProvider, EntityManager em) {
        ElementProviderRepositoryImp epr = new ElementProviderRepositoryImp();
        epr.setEm(em);
        epr.save(elementProvider);
    }

    protected void persistBudget(Budget budget, EntityManager em) {
        BudgetRepositoryImp br = new BudgetRepositoryImp();
        br.setEm(em);
        br.save(budget);
    }

    protected void persistOperation(Operation operation, EntityManager em) {
        persistElement(operation.getElement(), em);
        persistElementProvider(operation.getElementProvider(), em);

        OperationRepositoryImp or = new OperationRepositoryImp();
        or.setEm(em);
        or.save(operation);
    }

    protected void persistUser(User user, EntityManager em) {
        UserRepositoryImp ur = new UserRepositoryImp();
        ur.setEm(em);
        ur.save(user);
    }
}
