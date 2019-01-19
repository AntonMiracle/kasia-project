package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.EmailExistRuntimeException;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.exception.UserNameExistRuntimeException;
import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.UserBudget;
import com.kasia.model.UserConnectBudget;
import com.kasia.model.repository.UserBudgetRepository;
import com.kasia.model.repository.UserConnectBudgetRepository;
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
    private BudgetService bService;
    @Autowired
    private UserBudgetRepository ubRepository;
    @Autowired
    private UserConnectBudgetRepository ucbRepository;

    @After
    public void cleanData() {
        ubRepository.findAll().forEach(ubRepository::delete);
        ucbRepository.findAll().forEach(ucbRepository::delete);

        bService.findAllBudgets().forEach(bService::deleteBudget);
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
        User user = uService.saveUser(ModelTestData.getUser1());

        assertThat(uService.deleteUser(user)).isTrue();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenUserIdInvalidDeleteUserThrowException() {
        User user = uService.saveUser(ModelTestData.getUser1());
        user.setId(0);

        uService.deleteUser(user);
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidDeleteUserThrowException() {
        User user = uService.saveUser(ModelTestData.getUser1());
        user.setName("");

        uService.deleteUser(user);
    }

    @Test
    public void saveNewUser() {
        User expected = ModelTestData.getUser1();

        uService.saveUser(expected);

        User actual = uService.findUserById(expected.getId());
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
        String notUniqueEmail = uService.saveUser(ModelTestData.getUser1()).getEmail();
        String uniqueEmail = ModelTestData.getUser2().getEmail();

        assertThat(notUniqueEmail).isNotEqualTo(uniqueEmail);
        assertThat(uService.isUserEmailUnique(uniqueEmail)).isTrue();
        assertThat(uService.isUserEmailUnique(notUniqueEmail)).isFalse();
    }

    @Test
    public void userNameUnique() {
        String notUniqueName = uService.saveUser(ModelTestData.getUser1()).getName();
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

    @Test
    public void activatedUser() {
        User user = ModelTestData.getUser1();
        assertThat(uService.isActivated(user)).isFalse();

        uService.activate(user);

        assertThat(uService.isActivated(user)).isTrue();
    }

    @Test
    public void isActivated() {
        User user = ModelTestData.getUser1();
        assertThat(uService.isActivated(user)).isFalse();

        uService.saveUser(user);

        assertThat(uService.isActivated(user)).isTrue();
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidIsActivatedThrowException() {
        User user = ModelTestData.getUser1();
        user.setName("");
        uService.isActivated(user);
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidActivatedUserThrowException() {
        User user = ModelTestData.getUser1();
        user.setName("");

        uService.activate(user);
    }

    @Test
    public void deactivate() {
        User user = ModelTestData.getUser1();
        uService.saveUser(user);

        uService.deactivate(user);

        assertThat(uService.isActivated(user)).isFalse();
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidDeactivateThrowException() {
        User user = ModelTestData.getUser1();
        user.setName("");
        uService.deactivate(user);
    }

    @Test
    public void isLocaleAvailable() {
        assertThat(uService.isLocaleAvailable(uService.getDefaultLocale())).isTrue();
        assertThat(uService.isLocaleAvailable(new Locale("", ""))).isFalse();
    }

    @Test
    public void userOwnBudget() {
        User owner = uService.saveUser(ModelTestData.getUser1());
        User user = uService.saveUser(ModelTestData.getUser2());
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());

        UserBudget ub = ModelTestData.getUserBudget1();
        ub.getBudgets().clear();
        ub.getBudgets().add(budget);
        ub.setUser(owner);
        ubRepository.save(ub);

        assertThat(uService.isUserOwnBudget(budget, owner)).isTrue();
        assertThat(uService.isUserOwnBudget(budget, user)).isFalse();
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidIsUserOwnBudgetThrowException() {
        User user = ModelTestData.getUser1();
        user.setId(1);
        user.setName("");
        Budget budget = ModelTestData.getBudget1();
        budget.setId(1);

        uService.isUserOwnBudget(budget, user);
    }

    @Test(expected = ValidationException.class)
    public void whenBudgetInvalidIsUserOwnBudgetThrowException() {
        User user = ModelTestData.getUser1();
        user.setId(1);
        Budget budget = ModelTestData.getBudget1();
        budget.setId(1);
        budget.setName("");

        uService.isUserOwnBudget(budget, user);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidIsUserOwnBudgetThrowException() {
        User user = ModelTestData.getUser1();
        user.setId(1);
        Budget budget = ModelTestData.getBudget1();

        uService.isUserOwnBudget(budget, user);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenUserIdInvalidIsUserOwnBudgetThrowException() {
        User user = ModelTestData.getUser1();
        Budget budget = ModelTestData.getBudget1();
        budget.setId(1);

        uService.isUserOwnBudget(budget, user);
    }

    @Test
    public void userConnectToBudget() {
        User haveConnect = uService.saveUser(ModelTestData.getUser1());
        User noConnect = uService.saveUser(ModelTestData.getUser2());
        Budget withBudget = bService.saveBudget(ModelTestData.getBudget1());

        UserConnectBudget ucb = ModelTestData.getUserConnectBudget1();
        ucb.getConnectBudgets().clear();
        ucb.getConnectBudgets().add(withBudget);
        ucb.setUser(haveConnect);
        ucbRepository.save(ucb);

        assertThat(uService.isUserConnectToBudget(withBudget, haveConnect)).isTrue();
        assertThat(uService.isUserConnectToBudget(withBudget, noConnect)).isFalse();
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidIsUserConnectToBudgetThrowException() {
        User user = ModelTestData.getUser1();
        user.setId(1);
        user.setName("");
        Budget budget = ModelTestData.getBudget1();
        budget.setId(1);

        uService.isUserConnectToBudget(budget, user);
    }

    @Test(expected = ValidationException.class)
    public void whenBudgetInvalidIsUserConnectToBudgetThrowException() {
        User user = ModelTestData.getUser1();
        user.setId(1);
        Budget budget = ModelTestData.getBudget1();
        budget.setId(1);
        budget.setName("");

        uService.isUserConnectToBudget(budget, user);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidIsUserConnectToBudgetThrowException() {
        User user = ModelTestData.getUser1();
        user.setId(1);
        Budget budget = ModelTestData.getBudget1();

        uService.isUserConnectToBudget(budget, user);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenUserIdInvalidIsUserConnectToBudgetThrowException() {
        User user = ModelTestData.getUser1();
        Budget budget = ModelTestData.getBudget1();
        budget.setId(1);

        uService.isUserConnectToBudget(budget, user);
    }

    @Test
    public void findAllConnectedUser() {
        User haveConnect = uService.saveUser(ModelTestData.getUser1());
        Budget withBudget = bService.saveBudget(ModelTestData.getBudget1());

        UserConnectBudget ucb = ModelTestData.getUserConnectBudget1();
        ucb.getConnectBudgets().clear();
        ucb.getConnectBudgets().add(withBudget);
        ucb.setUser(haveConnect);
        ucbRepository.save(ucb);

        assertThat(uService.findConnectUsers(withBudget).size() == 1).isTrue();
    }

    @Test(expected = ValidationException.class)
    public void whenBudgetInvalidFindAllConnectedUserThrowException() {
        Budget budget = ModelTestData.getBudget1();
        budget.setId(1);
        budget.setName("");

        uService.findConnectUsers(budget);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidFindAllConnectedUserThrowException() {
        Budget budget = ModelTestData.getBudget1();

        uService.findConnectUsers(budget);
    }
}