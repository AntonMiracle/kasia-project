package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.EmailExistRuntimeException;
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

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Set;

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
        uService.findAllUsers().forEach(uService::deleteUser);
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

        uService.saveUser(expected);

        User actual = uRepository.findById(expected.getId()).get();
        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidSaveNewUserThrowException() {
        User expected = ModelTestData.getUser1();
        expected.setName("");

        uService.saveUser(expected);
    }

    private User getSavedUserForTest() {
        User savedUser = ModelTestData.getUser1();
        return uService.saveUser(savedUser);
    }

    @Test(expected = UserNameExistRuntimeException.class)
    public void whenUserNameNotUniqueSaveNewUserThrowException() {
        User notUniqueUsername = ModelTestData.getUser2();
        notUniqueUsername.setName(getSavedUserForTest().getName());

        uService.saveUser(notUniqueUsername);
    }

    @Test(expected = EmailExistRuntimeException.class)
    public void whenEmailNotUniqueSaveNewUserThrowException() {
        User notUniqueEmail = ModelTestData.getUser2();
        notUniqueEmail.setEmail(getSavedUserForTest().getEmail());

        uService.saveUser(notUniqueEmail);
    }

    @Test
    public void saveUserUpdate() {
        User expected = getSavedUserForTest();
        String expectedName = expected.getName() + expected.getName();
        String expectedEmail = "new" + expected.getEmail();
        expected.setName(expectedName);
        expected.setEmail(expectedEmail);

        uService.saveUser(expected);

        User actual = uService.findUserById(expected.getId());
        assertThat(actual.getName()).isEqualTo(expectedName);
        assertThat(actual.getEmail()).isEqualTo(expectedEmail);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenUserNameNotUniqueSaveUserDoNotUpdateUser() {
        User user1 = uService.saveUser(ModelTestData.getUser1());
        User user2 = uService.saveUser(ModelTestData.getUser2());
        user2.setName(user1.getName());

        uService.saveUser(user2);

        assertThat(uService.findUserById(user2.getId()).getName()).isNotEqualTo(user1.getName());
    }

    @Test
    public void whenUserEmailNotUniqueSaveUserDoNotUpdateUser() {
        User user1 = uService.saveUser(ModelTestData.getUser1());
        User user2 = uService.saveUser(ModelTestData.getUser2());
        user2.setEmail(user1.getEmail());

        uService.saveUser(user2);

        assertThat(uService.findUserById(user2.getId()).getEmail()).isNotEqualTo(user1.getEmail());
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenIdNegativeSaveUserThrowException() {
        User user1 = uService.saveUser(ModelTestData.getUser1());
        user1.setId(-1);
        uService.saveUser(user1);
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

    @Test
    public void findUserById() {
        User expected = getSavedUserForTest();

        User actual = uService.findUserById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenIdNotExistFindUserByIdReturnNull() {
        assertThat(uService.findUserById(22)).isNull();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenIdZeroFindUserByIdThrowException() {
        assertThat(uService.findUserById(0)).isNull();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenIdNegativeFindUserByIdThrowException() {
        assertThat(uService.findUserById(-1)).isNull();
    }

    @Test
    public void findAllUsers() {
        getSavedUserForTest();

        assertThat(uService.findAllUsers().size() == 1).isTrue();
    }

    @Test
    public void findUserByName() {
        User expected = getSavedUserForTest();

        assertThat(uService.findUserByName(expected.getName())).isEqualTo(expected);
    }

    @Test
    public void whenNameNotExistFindUserByNameReturnNull() {
        assertThat(uService.findUserByName(null)).isNull();
        assertThat(uService.findUserByName("")).isNull();
    }

    @Test
    public void findUserByEmail() {
        User expected = getSavedUserForTest();
        assertThat(uService.findUserByEmail(expected.getEmail())).isEqualTo(expected);
    }

    @Test
    public void whenEmailNotExistFindUserByEmailReturnNull() {
        assertThat(uService.findUserByEmail(null)).isNull();
        assertThat(uService.findUserByEmail("")).isNull();
    }

    @Test
    public void cryptPassword() {
        String nonCryptPassword = ModelTestData.getUser1().getPassword();
        String cryptPassword = "1be0222750aaf3889ab95b5d593ba12e4ff1046474702d6b4779f4b527305b230Aa";

        assertThat(uService.cryptPassword(nonCryptPassword)).isEqualTo(cryptPassword);
    }

    @Test
    public void zoneIdOf() {
        String validZone = "Pacific/Johnston";
        String invalidZoneId = "SomeWrongZoneId";

        assertThat(uService.zoneIdOf(validZone)).isEqualTo(ZoneId.of(validZone));
        assertThat(uService.zoneIdOf(invalidZoneId)).isEqualTo(ZoneId.systemDefault());
    }

    @Test
    public void whenLocaleInvalidLocaleOfReturnDefaultLocale() {
        Locale locale1 = uService.localeOf("asdf", "Fsda");
        User user = ModelTestData.getUser1();
        Locale locale2 = uService.localeOf(user.getLocale().getLanguage(), user.getLocale().getCountry());

        assertThat(locale1).isEqualTo(ModelTestData.getDefaultLocale());
        assertThat(uService.getCorrectAvailableLocales().contains(locale2)).isTrue();
    }

    @Test
    public void allCorrectAvailableLocalesHaveLangAndCountry() {
        Set<Locale> locales = uService.getCorrectAvailableLocales();
        int count = 0;
        for (Locale locale : Locale.getAvailableLocales()) {
            if (locale.getLanguage().length() > 0 && locale.getCountry().length() > 0) {
                count++;
                assertThat(locales.contains(locale)).isTrue();
            }
        }
        assertThat(locales.size() == count).isTrue();
    }
}