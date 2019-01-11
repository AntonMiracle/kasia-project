package com.kasia.repository;

import com.kasia.model.ElementProvider;
import com.kasia.model.ModelTestHelper;
import com.kasia.repository.imp.ElementProviderRepositoryImp;
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
import static org.assertj.core.api.Assertions.assertThat;

public class ElementProviderRepositoryIT {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_TEST_UNIT_NAME);
    private EntityManager em;
    private EntityTransaction et;
    private ElementProviderRepositoryImp repository;

    @Before
    public void before() throws NamingException {
        em = emf.createEntityManager();
        et = em.getTransaction();
        repository = new ElementProviderRepositoryImp();
        repository.setEm(em);
    }

    @After
    public void after() {
        if (em != null) em.close();
    }

    @Test
    public void save() throws Exception {
        ElementProvider elementProvider = ModelTestHelper.getElementProvider1();
        assertThat(elementProvider.getId() == 0).isTrue();

        saveForTest(elementProvider);

        assertThat(elementProvider.getId() > 0).isTrue();
    }

    private ElementProvider saveForTest(ElementProvider elementProvider) {
        et.begin();
        repository.save(elementProvider);
        et.commit();
        return elementProvider;
    }

    @Test
    public void getById() throws Exception {
        ElementProvider elementProvider = saveForTest(ModelTestHelper.getElementProvider1());
        long id = elementProvider.getId();

        elementProvider = repository.getById(id);

        assertThat(elementProvider).isNotNull();
        assertThat(elementProvider.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        ElementProvider elementProvider = ModelTestHelper.getElementProvider1();

        et.begin();
        repository.delete(elementProvider);
        et.commit();

        assertThat(repository.getById(elementProvider.getId())).isNull();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestHelper.getElementProvider1());
        saveForTest(ModelTestHelper.getElementProvider2());

        Set<ElementProvider> elementProviders = repository.getAll();

        assertThat(elementProviders).isNotNull();
        assertThat(elementProviders.size() == 2).isTrue();
    }

}