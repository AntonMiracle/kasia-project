package com.kasia.repository;

import com.kasia.ModelTestData;
import com.kasia.model.User;
import com.kasia.model.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSpringForTest {
    @Autowired
    private UserRepository repository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
    }

    @Test
    public void repositoryIsNotNull() {
        assertThat(repository).isNotNull();
    }

    @Test
    public void save() {
        User user = ModelTestData.getUser1();
        assertThat(user.getId() == 0).isTrue();

        saveForTest(user);

        assertThat(user.getId() > 0).isTrue();
    }

    private User saveForTest(User user) {
        repository.save(user);
        return user;
    }

    @Test
    public void getById() throws Exception {
        User user = saveForTest(ModelTestData.getUser1());
        long id = user.getId();

        user = repository.findById(id).get();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        User user = saveForTest(ModelTestData.getUser1());

        repository.delete(user);

        assertThat(repository.findById(user.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestData.getUser1());
        saveForTest(ModelTestData.getUser2());
        Set<User> users = new HashSet<>();

        repository.findAll().forEach(users::add);

        assertThat(users).isNotNull();
        assertThat(users.size() == 2).isTrue();
    }

    @Test
    public void getByEmail() {
        User user = ModelTestData.getUser1();
        String email = user.getEmail();
        saveForTest(user);

        user = repository.findByEmail(email).get();

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);

    }

    @Test
    public void whenEmailNotExistGetByEmailReturnNull() {
        assertThat(repository.findByEmail(ModelTestData.getUser1().getEmail()).isPresent()).isFalse();
    }

    @Test
    public void getByName() {
        User user = ModelTestData.getUser1();
        String name = user.getName();
        saveForTest(user);

        user = repository.findByName(name).get();

        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    public void whenNameNotExistGetByNameReturnNull() {
        assertThat(repository.findByName(ModelTestData.getUser1().getName()).isPresent()).isFalse();
    }

}