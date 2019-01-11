package com.kasia.repository;

import com.kasia.model.*;
import com.kasia.repository.imp.UserBudgetRepositoryImp;
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

public class UserBudgetRepositoryIT {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_TEST_UNIT_NAME);
    private EntityManager em;
    private EntityTransaction et;
    private UserBudgetRepositoryImp repository;

    @Before
    public void before() throws NamingException {
        em = emf.createEntityManager();
        et = em.getTransaction();
        repository = new UserBudgetRepositoryImp();
        repository.setEm(em);
    }

    @After
    public void after() {
        if (em != null) em.close();
    }

    @Test
    public void save() throws Exception {
        UserBudget userBudget = ModelTestHelper.getUserBudget1();
        assertThat(userBudget.getId() == 0).isTrue();

        saveForTest(userBudget);

        assertThat(userBudget.getId() > 0).isTrue();
    }

    private UserBudget saveForTest(UserBudget userBudget) {
        et.begin();

        for (Budget budget : userBudget.getBudgets()) {
            persistBudget(budget, em);
        }
        persistUser(userBudget.getUser(), em);

        repository.save(userBudget);
        et.commit();
        return userBudget;
    }

    @Test
    public void getById() throws Exception {
        UserBudget userBudget = saveForTest(ModelTestHelper.getUserBudget1());
        long id = userBudget.getId();

        userBudget = repository.getById(id);

        assertThat(userBudget).isNotNull();
        assertThat(userBudget.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        UserBudget userBudget = saveForTest(ModelTestHelper.getUserBudget1());

        et.begin();
        repository.delete(userBudget);
        et.commit();

        assertThat(repository.getById(userBudget.getId())).isNull();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestHelper.getUserBudget1());
        saveForTest(ModelTestHelper.getUserBudget2());

        Set<UserBudget> userBudgets = repository.getAll();

        assertThat(userBudgets).isNotNull();
        assertThat(userBudgets.size() == 2).isTrue();
    }
}