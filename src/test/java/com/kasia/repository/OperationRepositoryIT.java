package com.kasia.repository;

import com.kasia.model.ModelTestHelper;
import com.kasia.model.Operation;
import com.kasia.repository.imp.OperationRepositoryImp;
import org.junit.Before;
import org.junit.Test;

import javax.naming.NamingException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationRepositoryIT extends ModelRepositoryTestHelper {
    private OperationRepositoryImp repository;

    @Before
    public void setUp() throws NamingException {
        em = emf.createEntityManager();
        et = em.getTransaction();
        repository = new OperationRepositoryImp();
        repository.setEm(em);
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