package com.kasia.repository;

import com.kasia.model.ModelTestHelper;
import com.kasia.model.Operation;
import com.kasia.repository.imp.OperationRepositoryImp;
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
import static com.kasia.repository.ModelRepositoryTestHelper.persistElement;
import static com.kasia.repository.ModelRepositoryTestHelper.persistElementProvider;
import static org.assertj.core.api.Assertions.assertThat;

public class OperationRepositoryIT {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_TEST_UNIT_NAME);
    private EntityManager em;
    private EntityTransaction et;
    private OperationRepositoryImp repository;

    @Before
    public void before() throws NamingException {
        em = emf.createEntityManager();
        et = em.getTransaction();
        repository = new OperationRepositoryImp();
        repository.setEm(em);
    }

    @After
    public void after() {
        if (em != null) em.close();
    }

    @Test
    public void save() throws Exception {
        Operation operation = ModelTestHelper.getOperation1();
        assertThat(operation.getId() == 0).isTrue();

        saveForTest(operation);

        assertThat(operation.getId() > 0).isTrue();
    }

    private Operation saveForTest(Operation operation) {
        et.begin();
        persistElement(operation.getElement(), em);
        persistElementProvider(operation.getElementProvider(), em);
        repository.save(operation);
        et.commit();
        return operation;
    }


    @Test
    public void getById() throws Exception {
        Operation operation = saveForTest(ModelTestHelper.getOperation1());
        long id = operation.getId();

        operation = repository.getById(id);

        assertThat(operation).isNotNull();
        assertThat(operation.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        Operation operation = saveForTest(ModelTestHelper.getOperation1());

        et.begin();
        repository.delete(operation);
        et.commit();

        assertThat(repository.getById(operation.getId())).isNull();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestHelper.getOperation1());
        saveForTest(ModelTestHelper.getOperation2());

        Set<Operation> operations = repository.getAll();

        assertThat(operations).isNotNull();
        assertThat(operations.size() == 2).isTrue();
    }

}