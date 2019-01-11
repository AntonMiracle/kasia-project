package com.kasia.repository;

import com.kasia.model.*;
import com.kasia.repository.imp.BudgetOperationRepositoryImp;
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
import static com.kasia.repository.ModelRepositoryTestHelper.persistOperation;
import static org.assertj.core.api.Assertions.assertThat;

public class BudgetOperationRepositoryIT {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_TEST_UNIT_NAME);
    private EntityManager em;
    private EntityTransaction et;
    private BudgetOperationRepositoryImp repository;

    @Before
    public void before() throws NamingException {
        em = emf.createEntityManager();
        et = em.getTransaction();
        repository = new BudgetOperationRepositoryImp();
        repository.setEm(em);
    }

    @After
    public void after() {
        if (em != null) em.close();
    }

    @Test
    public void save() throws Exception {
        BudgetOperation budgetOperation = ModelTestHelper.getBudgetOperation1();
        assertThat(budgetOperation.getId() == 0).isTrue();

        saveForTest(budgetOperation);

        assertThat(budgetOperation.getId() > 0).isTrue();
    }

    private BudgetOperation saveForTest(BudgetOperation budgetOperation) {
        et.begin();
        for (Operation operation : budgetOperation.getOperations()) {
            persistOperation(operation, em);
        }
        persistBudget(budgetOperation.getBudget(), em);

        repository.save(budgetOperation);
        et.commit();
        return budgetOperation;
    }


    @Test
    public void getById() throws Exception {
        BudgetOperation budgetOperation = saveForTest(ModelTestHelper.getBudgetOperation1());
        long id = budgetOperation.getId();

        budgetOperation = repository.getById(id);

        assertThat(budgetOperation).isNotNull();
        assertThat(budgetOperation.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        BudgetOperation budget = saveForTest(ModelTestHelper.getBudgetOperation1());

        et.begin();
        repository.delete(budget);
        et.commit();

        assertThat(repository.getById(budget.getId())).isNull();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestHelper.getBudgetOperation1());
        saveForTest(ModelTestHelper.getBudgetOperation2());

        Set<BudgetOperation> budgets = repository.getAll();

        assertThat(budgets).isNotNull();
        assertThat(budgets.size() == 2).isTrue();
    }

}