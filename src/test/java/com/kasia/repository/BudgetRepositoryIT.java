package com.kasia.repository;

import com.kasia.model.Budget;
import com.kasia.model.ModelTestHelper;
import com.kasia.repository.imp.BudgetRepositoryImp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetRepositoryIT {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnitForTest");
    private BudgetRepositoryImp repository;
    private EntityManager em;
    private EntityTransaction et;

    @Before
    public void setUp() throws NamingException {
        em = emf.createEntityManager();
        et = em.getTransaction();
        repository = new BudgetRepositoryImp();
        repository.setEm(em);
    }

    @After
    public void tearDown() {
        if (em != null) em.close();
    }

    @Test
    public void save() throws Exception {
        Budget budget = ModelTestHelper.getBudget1();
        assertThat(budget.getId() == 0).isTrue();

        et.begin();
        repository.save(budget);
        et.commit();

        assertThat(budget.getId() > 0).isTrue();
    }

    private Budget saveForTest(Budget budget) {
        et.begin();
        repository.save(budget);
        et.commit();
        return budget;
    }

    @Test
    public void getById() throws Exception {
        Budget budget = saveForTest(ModelTestHelper.getBudget1());
        long id = budget.getId();

        budget = repository.getById(id);

        assertThat(budget).isNotNull();
        assertThat(budget.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        Budget budget = saveForTest(ModelTestHelper.getBudget1());

        et.begin();
        repository.delete(budget);
        et.commit();

        assertThat(repository.getById(budget.getId())).isNull();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestHelper.getBudget1());
        saveForTest(ModelTestHelper.getBudget2());

        Set<Budget> budgets = repository.getAll();

        assertThat(budgets).isNotNull();
        assertThat(budgets.size() == 2).isTrue();
    }

}