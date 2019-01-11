package com.kasia.repository;

import com.kasia.model.Element;
import com.kasia.model.ModelTestHelper;
import com.kasia.repository.imp.ElementRepositoryImp;
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

public class ElementRepositoryIT {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_TEST_UNIT_NAME);
    private EntityManager em;
    private EntityTransaction et;
    private ElementRepositoryImp repository;

    @Before
    public void before() throws NamingException {
        em = emf.createEntityManager();
        et = em.getTransaction();
        repository = new ElementRepositoryImp();
        repository.setEm(em);
    }

    @After
    public void after() {
        if (em != null) em.close();
    }

    @Test
    public void save() throws Exception {
        Element element = ModelTestHelper.getElement1();
        assertThat(element.getId() == 0).isTrue();

        saveForTest(element);

        assertThat(element.getId() > 0).isTrue();
    }

    private Element saveForTest(Element element) {
        et.begin();
        repository.save(element);
        et.commit();
        return element;
    }

    @Test
    public void getById() throws Exception {
        Element element = saveForTest(ModelTestHelper.getElement1());
        long id = element.getId();

        element = repository.getById(id);

        assertThat(element).isNotNull();
        assertThat(element.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        Element element = saveForTest(ModelTestHelper.getElement1());

        et.begin();
        repository.delete(element);
        et.commit();

        assertThat(repository.getById(element.getId())).isNull();
    }

    @Test
    public void getAll() throws Exception {

        saveForTest(ModelTestHelper.getElement1());
        saveForTest(ModelTestHelper.getElement2());

        Set<Element> elements = repository.getAll();

        assertThat(elements).isNotNull();
        assertThat(elements.size() == 2).isTrue();
    }

}