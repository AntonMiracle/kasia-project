package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryIT {
    @Autowired
    private UserRepository repository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
    }

    @Test
    public void correctLocaleConvert() {
        User user = ModelTestData.user();
        Locale locale = new Locale("en", "CA");
        user.setLocale(locale);

        repository.save(user);

        assertThat(repository.findById(user.getId()).get().getLocale()).isEqualTo(locale);

    }

    @Test
    public void save() {
        User user = ModelTestData.user();
        assertThat(user.getId() == 0).isTrue();

        saveForTest(user);

        assertThat(user.getId() > 0).isTrue();
    }

    private User saveForTest(User user) {
        repository.save(user);
        return user;
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void whenSaveWithNonUniqueEmailThenException() {
        System.out.println("================= Must be DataIntegrityViolationException or ConstraintViolationException");
        User user1 = ModelTestData.user();
        User user2 = ModelTestData.user();
        assertThat(user1.getEmail()).isEqualTo(user2.getEmail());

        saveForTest(user1);
        saveForTest(user2);
    }

    @Test
    public void findById() throws Exception {
        User user = saveForTest(ModelTestData.user());
        long id = user.getId();

        user = repository.findById(id).get();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        User user = saveForTest(ModelTestData.user());

        repository.delete(user);

        assertThat(repository.findById(user.getId()).isPresent()).isFalse();
    }

    @Test
    public void findAll() throws Exception {
        User user1 = ModelTestData.user();
        User user2 = ModelTestData.user();
        user2.setEmail("new" + user1.getEmail());
        saveForTest(user1);
        saveForTest(user2);
        Set<User> users = new HashSet<>();

        repository.findAll().forEach(users::add);

        assertThat(users.size() == 2).isTrue();
    }

    @Test
    public void findByEmail() {
        User user = ModelTestData.user();
        String email = user.getEmail();
        saveForTest(user);

        user = repository.findByEmail(email).get();

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo(email);

    }

    @Test
    public void whenEmailNotExistGetByEmailReturnNull() {
        assertThat(repository.findByEmail(ModelTestData.user().getEmail()).isPresent()).isFalse();
    }

}