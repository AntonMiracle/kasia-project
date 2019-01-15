package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.EmailExistRuntimeException;
import com.kasia.exception.UserNameExistRuntimeException;
import com.kasia.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIT {
    @Autowired
    private UserService userService;

    @After
    public void after() {
        for (User user : userService.findAll()) userService.delete(user);
    }

    @Test
    public void create() throws Exception {
        User user = ModelTestData.getUser1();
        String nonCryptPassword = user.getPassword();
        user = userService.create(user.getEmail(), user.getName(), user.getPassword(), user.getZoneId());

        assertThat(user.getId() == 0).isTrue();
        assertThat(user.getZoneId()).isNotNull();
        assertThat(user.getPassword()).isNotNull();
        assertThat(user.getPassword().length()).isNotEqualTo(nonCryptPassword.length());
        assertThat(user.getEmail()).isNotNull();
        assertThat(user.getName()).isNotNull();
        assertThat(user.getCreateOn().compareTo(LocalDateTime.now().plusSeconds(1)) < 0).isNotNull();
    }

    @Test
    public void isEmailUnique() throws Exception {
        User user = ModelTestData.getUser1();
        userService.save(user);

        assertThat(userService.isEmailUnique(user.getEmail())).isFalse();
        assertThat(userService.isEmailUnique(ModelTestData.getUser2().getEmail())).isTrue();
    }

    @Test
    public void isNameUnique() throws Exception {
        User user = ModelTestData.getUser1();
        userService.save(user);

        assertThat(userService.isNameUnique(user.getName())).isFalse();
        assertThat(userService.isNameUnique(ModelTestData.getUser2().getName())).isTrue();
    }

    @Test
    public void crypt() throws Exception {
        String nonCryptPassword = ModelTestData.getUser1().getPassword();
        String cryptPassword = "1be0222750aaf3889ab95b5d593ba12e4ff1046474702d6b4779f4b527305b230Aa";

        assertThat(userService.crypt(nonCryptPassword)).isEqualTo(cryptPassword);
    }

    @Test
    public void zoneIdOf() throws Exception {
        String validZone = "Pacific/Johnston";
        String invalidZoneId = "SomeWrongZoneId";

        assertThat(userService.zoneIdOf(validZone)).isEqualTo(ZoneId.of("Pacific/Johnston"));
        assertThat(userService.zoneIdOf(invalidZoneId)).isEqualTo(ZoneId.systemDefault());
    }

    @Test
    public void findByName() throws Exception {
        User user = ModelTestData.getUser1();
        userService.save(user);

        assertThat(userService.findByName(user.getName())).isEqualTo(user);
        assertThat(userService.findByName(ModelTestData.getUser2().getName())).isNull();
    }

    @Test
    public void findByEmail() throws Exception {
        User user = ModelTestData.getUser1();
        userService.save(user);

        assertThat(userService.findByEmail(user.getEmail())).isEqualTo(user);
        assertThat(userService.findByEmail(ModelTestData.getUser2().getEmail())).isNull();
    }

    @Test
    public void saveNewUser() {
        User user = ModelTestData.getUser1();
        long idBeforeSave = user.getId();

        userService.save(user);

        assertThat(user.getId() > 0).isTrue();
        assertThat(user.getId() != idBeforeSave).isTrue();
        assertThat(userService.findById(user.getId())).isNotNull();
    }

    @Test
    public void saveUpdateUser() {
        User user = ModelTestData.getUser1();
        String newEmail = ModelTestData.getUser2().getEmail();
        userService.save(user);
        user.setEmail(newEmail);

        userService.save(user);

        assertThat(userService.findByEmail(newEmail)).isEqualTo(user);
    }

    @Test(expected = EmailExistRuntimeException.class)
    public void whenSaveNewUserWithNonUniqueEmailThenException() {
        User uniqueUser = ModelTestData.getUser1();
        User nonUniqueUser = ModelTestData.getUser2();
        nonUniqueUser.setEmail(uniqueUser.getEmail());

        userService.save(uniqueUser);
        userService.save(nonUniqueUser);
    }

    @Test(expected = UserNameExistRuntimeException.class)
    public void whenSaveNewUserWithNonUniqueNameThenException() {
        User uniqueUser = ModelTestData.getUser1();
        User nonUniqueUser = ModelTestData.getUser2();
        nonUniqueUser.setName(uniqueUser.getName());

        userService.save(uniqueUser);
        userService.save(nonUniqueUser);
    }

    @Test
    public void delete() {
        User user = ModelTestData.getUser1();
        userService.save(user);

        userService.delete(user);

        assertThat(userService.findById(user.getId())).isNull();
    }

    @Test
    public void findById() {
        User user = ModelTestData.getUser1();

        userService.save(user);

        assertThat(userService.findById(user.getId())).isEqualTo(user);
        assertThat(userService.findById(user.getId() + 1)).isNull();
    }
}