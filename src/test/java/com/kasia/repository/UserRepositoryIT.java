package com.kasia.repository;

import com.kasia.model.ModelTestHelper;
import com.kasia.model.User;
import com.kasia.repository.imp.UserRepositoryImp;
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

public class UserRepositoryIT {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_TEST_UNIT_NAME);
    private EntityManager em;
    private EntityTransaction et;
    private UserRepositoryImp repository;

    @Before
    public void before() throws NamingException {
        em = emf.createEntityManager();
        et = em.getTransaction();
        repository = new UserRepositoryImp();
        repository.setEm(em);

    }

    @After
    public void after() {
        if (em != null) em.close();
    }

    @Test
    public void save() throws Exception {
        User user = ModelTestHelper.getUser1();
        assertThat(user.getId() == 0).isTrue();

        saveForTest(user);
        assertThat(user.getId() > 0).isTrue();
    }

    private User saveForTest(User user) {
        et.begin();
        repository.save(user);
        et.commit();
        return user;
    }

    @Test
    public void getById() throws Exception {
        User user = saveForTest(ModelTestHelper.getUser1());
        long id = user.getId();

        user = repository.getById(id);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        User user = saveForTest(ModelTestHelper.getUser1());

        et.begin();
        repository.delete(user);
        et.commit();

        assertThat(repository.getById(user.getId())).isNull();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestHelper.getUser1());
        saveForTest(ModelTestHelper.getUser2());

        Set<User> users = repository.getAll();

        assertThat(users).isNotNull();
        assertThat(users.size() == 2).isTrue();
    }

    @Test
    public void getByEmail() {
        User user = ModelTestHelper.getUser1();
        String email = "opps@gmail.com";
        user.setEmail(email);
        saveForTest(user);

        user = repository.getByEmail(email);

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);

    }

    @Test
    public void whenEmailNotExistGetByEmailReturnNull() {
        assertThat(repository.getByEmail("iiii@gmail.com")).isNull();
    }

    @Test
    public void getByName() {
        User user = ModelTestHelper.getUser1();
        String name = "someName";
        user.setName(name);
        saveForTest(user);

        user = repository.getByName(name);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    public void whenNameNotExistGetByNameReturnNull() {
        assertThat(repository.getByName("iiii@gmail.com")).isNull();
    }
}