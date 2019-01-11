package com.kasia.repository;

import com.kasia.model.BudgetElementProvider;
import com.kasia.model.ElementProvider;
import com.kasia.model.ModelTestHelper;
import com.kasia.repository.imp.BudgetElementProviderRepositoryImp;
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
import static com.kasia.repository.ModelRepositoryTestHelper.persistElementProvider;
import static org.assertj.core.api.Assertions.assertThat;

public class BudgetElementProviderRepositoryIT {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_TEST_UNIT_NAME);
    private EntityManager em;
    private EntityTransaction et;
    private BudgetElementProviderRepositoryImp repository;

    @Before
    public void before() throws NamingException {
        em = emf.createEntityManager();
        et = em.getTransaction();
        repository = new BudgetElementProviderRepositoryImp();
        repository.setEm(em);
    }

    @After
    public void after() {
        if (em != null) em.close();
    }

    @Test
    public void save() throws Exception {
        BudgetElementProvider budgetElementProvider = ModelTestHelper.getBudgetElementProvider1();
        assertThat(budgetElementProvider.getId() == 0).isTrue();

        saveForTest(budgetElementProvider);

        assertThat(budgetElementProvider.getId() > 0).isTrue();
    }

    private BudgetElementProvider saveForTest(BudgetElementProvider budgetElementProvider) {
        et.begin();

        for (ElementProvider provider : budgetElementProvider.getElementProviders()) {
            persistElementProvider(provider, em);
        }
        persistBudget(budgetElementProvider.getBudget(), em);

        repository.save(budgetElementProvider);
        et.commit();
        return budgetElementProvider;
    }

    @Test
    public void getById() throws Exception {
        BudgetElementProvider budgetElementProvider = saveForTest(ModelTestHelper.getBudgetElementProvider1());
        long id = budgetElementProvider.getId();

        budgetElementProvider = repository.getById(id);

        assertThat(budgetElementProvider).isNotNull();
        assertThat(budgetElementProvider.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        BudgetElementProvider budgetElementProvider = saveForTest(ModelTestHelper.getBudgetElementProvider1());

        et.begin();
        repository.delete(budgetElementProvider);
        et.commit();

        assertThat(repository.getById(budgetElementProvider.getId())).isNull();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestHelper.getBudgetElementProvider1());
        saveForTest(ModelTestHelper.getBudgetElementProvider2());

        Set<BudgetElementProvider> budgetElementProviders = repository.getAll();

        assertThat(budgetElementProviders).isNotNull();
        assertThat(budgetElementProviders.size() == 2).isTrue();
    }
}