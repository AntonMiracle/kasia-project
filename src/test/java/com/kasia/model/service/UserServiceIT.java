package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.exception.UserNameExistRuntimeException;
import com.kasia.model.User;
import com.kasia.model.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIT {
    @Autowired
    private UserService uService;
    @Autowired
    private UserRepository uRepository;

    @After
    public void cleanData() {
        uRepository.findAll().forEach(uRepository::delete);
//        uService.findAllUsers().forEach(uService::deleteUser);
    }

    @Test
    public void createUser() {
        User expected = ModelTestData.getUser1();

        User actual = uService.createUser(expected.getEmail(), expected.getName()
                , expected.getPassword(), expected.getZoneId(), expected.getLocale());

        assertThat(actual.getId() == 0).isTrue();
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getRole()).isEqualTo(expected.getRole());
        assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
        assertThat(actual.getZoneId()).isEqualTo(expected.getZoneId());
        assertThat(actual.getLocale()).isEqualTo(expected.getLocale());
        assertThat(actual.getCreateOn().compareTo(LocalDateTime.now().plusSeconds(2)) < 0).isTrue();
    }

    @Test
    public void deleteUser() {
        User user = uRepository.save(ModelTestData.getUser1());

        assertThat(uService.deleteUser(user)).isTrue();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenUserIdInvalidDeleteUserThrowException() {
        User user = uRepository.save(ModelTestData.getUser1());
        user.setId(0);

        uService.deleteUser(user);
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidDeleteUserThrowException() {
        User user = uRepository.save(ModelTestData.getUser1());
        user.setName("");

        uService.deleteUser(user);
    }

    @Test
    public void saveNewUser() {
        User expected = ModelTestData.getUser1();

        uService.save(expected);

        User actual = uRepository.findById(expected.getId()).get();
        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidSaveNewUserThrowException() {
        User expected = ModelTestData.getUser1();
        expected.setName("");

        uService.save(expected);
    }

    @Test(expected = UserNameExistRuntimeException.class)
    public void whenUserNameNotUniqueSaveNewUserThrowException() {
        throw new NotImplementedException();
//        User savedUser = ModelTestData.getUser1();
//        uService.save(savedUser);
//
//        User notUniqueUsername = ModelTestData.getUser2();
//        notUniqueUsername.setName(savedUser.getName());
//
//        uService.save(notUniqueUsername);
    }

    @Test
    public void userEmailUnique() {
        String notUniqueEmail = uRepository.save(ModelTestData.getUser1()).getEmail();
        String uniqueEmail = ModelTestData.getUser2().getEmail();

        assertThat(notUniqueEmail).isNotEqualTo(uniqueEmail);
        assertThat(uService.isUserEmailUnique(uniqueEmail)).isTrue();
        assertThat(uService.isUserEmailUnique(notUniqueEmail)).isFalse();
    }

    @Test
    public void userNameUnique() {
        String notUniqueName = uRepository.save(ModelTestData.getUser1()).getName();
        String uniqueName = ModelTestData.getUser2().getName();

        assertThat(notUniqueName).isNotEqualTo(uniqueName);
        assertThat(uService.isUserNameUnique(uniqueName)).isTrue();
        assertThat(uService.isUserNameUnique(notUniqueName)).isFalse();
    }
}