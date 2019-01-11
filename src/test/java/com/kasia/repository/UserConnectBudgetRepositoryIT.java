package com.kasia.repository;

import com.kasia.model.*;
import com.kasia.repository.imp.UserConnectBudgetRepositoryImp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Set;

import static com.kasia.repository.ModelRepositoryTestHelper.PERSISTENCE_TEST_UNIT_NAME;
import static com.kasia.repository.ModelRepositoryTestHelper.persistBudget;
import static com.kasia.repository.ModelRepositoryTestHelper.persistUser;
import static org.assertj.core.api.Assertions.assertThat;

public class UserConnectBudgetRepositoryIT {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_TEST_UNIT_NAME);
    private EntityManager em;
    private EntityTransaction et;
    private UserConnectBudgetRepositoryImp repository;

    @Before
    public void before() throws NamingException {
        em = emf.createEntityManager();
        et = em.getTransaction();
        repository = new UserConnectBudgetRepositoryImp();
        repository.setEm(em);

    }

    @After
    public void after() {
        if (em != null) em.close();
    }

    @Test
    public void save() throws Exception {
        UserConnectBudget userConnectBudget = ModelTestHelper.getUserConnectBudget1();
        assertThat(userConnectBudget.getId() == 0).isTrue();

        saveForTest(userConnectBudget);

        assertThat(userConnectBudget.getId() > 0).isTrue();
    }

    private UserConnectBudget saveForTest(UserConnectBudget userConnectBudget) {
        et.begin();
        for (Budget budget : userConnectBudget.getConnectBudgets()) {
            persistBudget(budget, em);
        }
        persistUser(userConnectBudget.getUser(), em);

        repository.save(userConnectBudget);
        et.commit();
        return userConnectBudget;
    }

    @Test
    public void getById() throws Exception {
        UserConnectBudget userConnectBudget = saveForTest(ModelTestHelper.getUserConnectBudget1());
        long id = userConnectBudget.getId();

        userConnectBudget = repository.getById(id);

        assertThat(userConnectBudget).isNotNull();
        assertThat(userConnectBudget.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        UserConnectBudget userConnectBudget = saveForTest(ModelTestHelper.getUserConnectBudget1());

        et.begin();
        repository.delete(userConnectBudget);
        et.commit();

        assertThat(repository.getById(userConnectBudget.getId())).isNull();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestHelper.getUserConnectBudget1());
        saveForTest(ModelTestHelper.getUserConnectBudget2());

        Set<UserConnectBudget> budgets = repository.getAll();

        assertThat(budgets).isNotNull();
        assertThat(budgets.size() == 2).isTrue();
    }

}