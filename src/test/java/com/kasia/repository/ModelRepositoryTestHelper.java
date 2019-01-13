package com.kasia.repository;

import com.kasia.model.*;
import com.kasia.repository.imp.*;

import javax.persistence.EntityManager;

public class ModelRepositoryTestHelper {
    final static String PERSISTENCE_TEST_UNIT_NAME = "test";

    static void persistElement(Element element, EntityManager em) {
        ElementRepositoryImp er = new ElementRepositoryImp();
        er.setEm(em);
        er.save(element);
    }

    static void persistElementProvider(ElementProvider elementProvider, EntityManager em) {
        ElementProviderRepositoryImp epr = new ElementProviderRepositoryImp();
        epr.setEm(em);
        epr.save(elementProvider);
    }

    static void persistBudget(Budget budget, EntityManager em) {
        BudgetRepositoryImp br = new BudgetRepositoryImp();
        br.setEm(em);
        br.save(budget);
    }

    static void persistOperation(Operation operation, EntityManager em) {
        persistElement(operation.getElement(), em);
        persistElementProvider(operation.getElementProvider(), em);

        OperationRepositoryImp or = new OperationRepositoryImp();
        or.setEm(em);
        or.save(operation);
    }

    static void persistUser(User user, EntityManager em) {
        UserRepositoryImp ur = new UserRepositoryImp();
        ur.setEm(em);
        ur.save(user);
    }
}
