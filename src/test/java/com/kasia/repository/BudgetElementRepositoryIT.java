package com.kasia.repository;

import com.kasia.model.*;
import com.kasia.repository.imp.BudgetElementRepositoryImp;
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
import static com.kasia.repository.ModelRepositoryTestHelper.persistElement;
import static org.assertj.core.api.Assertions.assertThat;

public class BudgetElementRepositoryIT {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_TEST_UNIT_NAME);
    private EntityManager em;
    private EntityTransaction et;
    private BudgetElementRepositoryImp repository;

    @Before
    public void before() throws NamingException {
        em = emf.createEntityManager();
        et = em.getTransaction();
        repository = new BudgetElementRepositoryImp();
        repository.setEm(em);
    }

    @After
    public void after() {
        if (em != null) em.close();
    }

    @Test
    public void save() throws Exception {
        BudgetElement budgetElement = ModelTestHelper.getBudgetElement1();
        assertThat(budgetElement.getId() == 0).isTrue();

        saveForTest(budgetElement);

        assertThat(budgetElement.getId() > 0).isTrue();
    }

    private BudgetElement saveForTest(BudgetElement budgetElement) {
        et.begin();

        for (Element element : budgetElement.getElements()) {
            persistElement(element, em);
        }
        persistBudget(budgetElement.getBudget(), em);

        repository.save(budgetElement);
        et.commit();
        return budgetElement;
    }

    @Test
    public void getById() throws Exception {
        BudgetElement budgetElement = saveForTest(ModelTestHelper.getBudgetElement1());
        long id = budgetElement.getId();

        budgetElement = repository.getById(id);

        assertThat(budgetElement).isNotNull();
        assertThat(budgetElement.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        BudgetElement budgetElement = saveForTest(ModelTestHelper.getBudgetElement1());

        et.begin();
        repository.delete(budgetElement);
        et.commit();

        assertThat(repository.getById(budgetElement.getId())).isNull();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestHelper.getBudgetElement1());
        saveForTest(ModelTestHelper.getBudgetElement2());

        Set<BudgetElement> budgetElements = repository.getAll();

        assertThat(budgetElements).isNotNull();
        assertThat(budgetElements.size() == 2).isTrue();
    }

}