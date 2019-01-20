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
        uService.findAllUsers().forEach(user -> uService.deleteUser(user.getId()));
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
        User u1 = ModelTestData.getUser1();
        User u2 = ModelTestData.getUser1();
        u1.setId(0);
        u2.setId(-1);

        assertThat(uService.deleteUser(user.getId())).isTrue();
        assertThat(uService.deleteUser(u1.getId())).isFalse();
        assertThat(uService.deleteUser(u2.getId())).isFalse();
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
        assertThat(uService.isUserEmailUnique(null)).isFalse();
    }

    @Test
    public void userNameUnique() {
        String notUniqueName = uService.saveUser(ModelTestData.getUser1()).getName();
        String uniqueName = ModelTestData.getUser2().getName();

        assertThat(notUniqueName).isNotEqualTo(uniqueName);
        assertThat(uService.isUserNameUnique(uniqueName)).isTrue();
        assertThat(uService.isUserNameUnique(notUniqueName)).isFalse();
        assertThat(uService.isUserNameUnique(null)).isFalse();
    }

    @Test
    public void findUserById() {
        User expected = getSavedUserForTest();

        User actual = uService.findUserById(expected.getId());

        assertThat(actual).isEqualTo(expected);
        assertThat(uService.findUserById(0)).isNull();
        assertThat(uService.findUserById(-1)).isNull();
    }

    @Test
    public void whenIdNotExistFindUserByIdReturnNull() {
        assertThat(uService.findUserById(22)).isNull();
        assertThat(uService.findUserById(-22)).isNull();
        assertThat(uService.findUserById(0)).isNull();
    }

    @Test
    public void findAllUsers() {
        assertThat(uService.findAllUsers().size() == 0).isTrue();
        getSavedUserForTest();

        assertThat(uService.findAllUsers().size() == 1).isTrue();
    }

    @Test
    public void findUserByName() {
        User expected = getSavedUserForTest();

        assertThat(uService.findUserByName(expected.getName())).isEqualTo(expected);
        assertThat(uService.findUserByName(null)).isNull();
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
        assertThat(uService.findUserByEmail(null)).isNull();
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
        assertThat(uService.cryptPassword(null)).isEqualTo("");
    }

    @Test
    public void zoneIdOf() {
        String validZone = "Pacific/Johnston";
        String invalidZoneId = "SomeWrongZoneId";

        assertThat(uService.zoneIdOf(validZone)).isEqualTo(ZoneId.of(validZone));
        assertThat(uService.zoneIdOf(invalidZoneId)).isEqualTo(ZoneId.systemDefault());
        assertThat(uService.zoneIdOf(null)).isEqualTo(ZoneId.systemDefault());
    }

    @Test
    public void whenLocaleInvalidLocaleOfReturnDefaultLocale() {
        Locale locale1 = uService.localeOf("asdf", "Fsda");
        User user = ModelTestData.getUser1();
        Locale locale2 = uService.localeOf(user.getLocale().getLanguage(), user.getLocale().getCountry());
        Locale localeNullC = uService.localeOf("pl", null);
        Locale localeNullL = uService.localeOf(null, "PL");

        assertThat(locale1).isEqualTo(uService.getDefaultLocale());
        assertThat(localeNullC).isEqualTo(uService.getDefaultLocale());
        assertThat(localeNullL).isEqualTo(uService.getDefaultLocale());
        assertThat(uService.getCorrectAvailableLocales().contains(locale2)).isTrue();
        assertThat(uService.getCorrectAvailableLocales().contains(localeNullC)).isTrue();
        assertThat(uService.getCorrectAvailableLocales().contains(localeNullL)).isTrue();
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

        assertThat(uService.activate(user)).isTrue();

        assertThat(uService.isActivated(user)).isTrue();
        assertThat(uService.activate(null)).isFalse();
    }

    @Test
    public void isActivated() {
        User user = ModelTestData.getUser1();
        assertThat(uService.isActivated(user)).isFalse();

        uService.saveUser(user);

        assertThat(uService.isActivated(user)).isTrue();
        assertThat(uService.isActivated(null)).isFalse();
    }

    @Test
    public void deactivate() {
        User user = ModelTestData.getUser1();
        uService.saveUser(user);

        assertThat(uService.deactivate(user)).isTrue();
        assertThat(uService.deactivate(null)).isFalse();
        assertThat(uService.isActivated(user)).isFalse();
    }

    @Test
    public void isLocaleAvailable() {
        assertThat(uService.isLocaleAvailable(uService.getDefaultLocale())).isTrue();
        assertThat(uService.isLocaleAvailable(new Locale("", ""))).isFalse();
        assertThat(uService.isLocaleAvailable(null)).isFalse();
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

        assertThat(uService.isUserOwnBudget(budget.getId(), owner.getId())).isTrue();
        assertThat(uService.isUserOwnBudget(budget.getId(), user.getId())).isFalse();
    }

    @Test
    public void userOwnBudgetWithInvalidIdReturnFalse() {
        User u1 = ModelTestData.getUser1();
        User u2 = ModelTestData.getUser2();
        u1.setId(0);
        u2.setId(-1);
        Budget b1 = ModelTestData.getBudget1();
        Budget b2 = ModelTestData.getBudget2();
        b1.setId(0);
        b2.setId(-1);

        assertThat(uService.isUserOwnBudget(b1.getId(), u1.getId())).isFalse();
        assertThat(uService.isUserOwnBudget(b1.getId(), u2.getId())).isFalse();
        assertThat(uService.isUserOwnBudget(b2.getId(), u1.getId())).isFalse();
        assertThat(uService.isUserOwnBudget(b2.getId(), u2.getId())).isFalse();
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

        assertThat(uService.isUserConnectToBudget(withBudget.getId(), haveConnect.getId())).isTrue();
        assertThat(uService.isUserConnectToBudget(withBudget.getId(), noConnect.getId())).isFalse();
    }

    @Test
    public void userConnectToBudgetWithInvalidIdReturnFalse() {
        User u1 = ModelTestData.getUser1();
        User u2 = ModelTestData.getUser2();
        u1.setId(0);
        u2.setId(-1);
        Budget b1 = ModelTestData.getBudget1();
        Budget b2 = ModelTestData.getBudget2();
        b1.setId(0);
        b2.setId(-1);

        assertThat(uService.isUserConnectToBudget(b1.getId(), u1.getId())).isFalse();
        assertThat(uService.isUserConnectToBudget(b1.getId(), u2.getId())).isFalse();
        assertThat(uService.isUserConnectToBudget(b2.getId(), u1.getId())).isFalse();
        assertThat(uService.isUserConnectToBudget(b2.getId(), u2.getId())).isFalse();
    }

    @Test
    public void findConnectUsers() {
        User haveConnect = uService.saveUser(ModelTestData.getUser1());
        Budget withBudget = bService.saveBudget(ModelTestData.getBudget1());
        Budget b1 = ModelTestData.getBudget1();
        Budget b2 = ModelTestData.getBudget2();
        b1.setId(0);
        b2.setId(-1);

        UserConnectBudget ucb = ModelTestData.getUserConnectBudget1();
        ucb.getConnectBudgets().clear();
        ucb.getConnectBudgets().add(withBudget);
        ucb.setUser(haveConnect);
        ucbRepository.save(ucb);

        assertThat(uService.findConnectUsers(withBudget.getId()).size() == 1).isTrue();
        assertThat(uService.findConnectUsers(b1.getId()).size() == 0).isTrue();
        assertThat(uService.findConnectUsers(b2.getId()).size() == 0).isTrue();
    }

    @Test
    public void findOwnBudgets() {
        User owner = uService.saveUser(ModelTestData.getUser1());
        User user = uService.saveUser(ModelTestData.getUser2());
        User owner2 = ModelTestData.getUser1();
        owner2.setId(0);
        User owner3 = ModelTestData.getUser2();
        owner3.setId(-1);

        Budget budget = bService.saveBudget(ModelTestData.getBudget1());

        UserBudget ub = ModelTestData.getUserBudget1();
        ub.getBudgets().clear();
        ub.getBudgets().add(budget);
        ub.setUser(owner);
        ubRepository.save(ub);

        assertThat(uService.findOwnBudgets(owner.getId()).size() == 1).isTrue();
        assertThat(uService.findOwnBudgets(owner.getId()).contains(budget)).isTrue();
        assertThat(uService.findOwnBudgets(user.getId()).size() == 0).isTrue();
        assertThat(uService.findOwnBudgets(owner2.getId()).size() == 0).isTrue();
        assertThat(uService.findOwnBudgets(owner3.getId()).size() == 0).isTrue();
    }

    @Test
    public void findOwner() {
        User owner1 = uService.saveUser(ModelTestData.getUser1());

        Budget budget1 = bService.saveBudget(ModelTestData.getBudget1());
        Budget budget2 = bService.saveBudget(ModelTestData.getBudget2());
        Budget budget3 = ModelTestData.getBudget1();
        budget3.setId(0);
        Budget budget4 = ModelTestData.getBudget1();
        budget4.setId(-1);

        UserBudget ub = ModelTestData.getUserBudget1();
        ub.getBudgets().clear();
        ub.getBudgets().add(budget1);
        ub.setUser(owner1);
        ubRepository.save(ub);

        assertThat(uService.findOwner(budget1.getId())).isEqualTo(owner1);
        assertThat(uService.findOwner(budget2.getId())).isNull();
        assertThat(uService.findOwner(budget3.getId())).isNull();
        assertThat(uService.findOwner(budget4.getId())).isNull();
        assertThat(uService.findOwnBudgets(owner1.getId()).size() == 1).isTrue();
    }

    @Test
    public void addBudget() {
        User owner1 = uService.saveUser(ModelTestData.getUser1());
        User owner2 = uService.saveUser(ModelTestData.getUser2());

        User connect1 = ModelTestData.getUser1();
        User connect2 = ModelTestData.getUser1();
        User connect3 = ModelTestData.getUser1();
        connect1.setName("new1" + connect1.getName());
        connect1.setEmail("new1" + connect1.getEmail());
        connect2.setName("new2" + connect2.getName());
        connect2.setEmail("new2" + connect2.getEmail());
        connect3.setName("new3" + connect3.getName());
        connect3.setEmail("new3" + connect3.getEmail());
        uService.saveUser(connect1);
        uService.saveUser(connect2);
        uService.saveUser(connect3);

        Budget budget1 = bService.saveBudget(ModelTestData.getBudget1());
        Budget budget2 = bService.saveBudget(ModelTestData.getBudget2());

        uService.addBudget(owner1.getId(), budget1.getId());
        uService.addBudget(owner2.getId(), budget1.getId());
        uService.addBudget(connect1.getId(), budget1.getId());

        uService.addBudget(owner2.getId(), budget2.getId());
        uService.addBudget(owner1.getId(), budget2.getId());
        uService.addBudget(connect2.getId(), budget2.getId());
        uService.addBudget(connect3.getId(), budget2.getId());

        assertThat(uService.findOwner(budget1.getId())).isEqualTo(owner1);
        assertThat(uService.findOwner(budget2.getId())).isEqualTo(owner2);
        assertThat(uService.findConnectUsers(budget1.getId()).size() == 2).isTrue();
        assertThat(uService.findConnectUsers(budget2.getId()).size() == 3).isTrue();
    }

    @Test
    public void addBudgetWithInvalidIdReturnFalse() {
        User u1 = ModelTestData.getUser1();
        User u2 = ModelTestData.getUser2();
        u1.setId(0);
        u2.setId(-1);

        Budget b1 = ModelTestData.getBudget1();
        Budget b2 = ModelTestData.getBudget2();
        b1.setId(0);
        b2.setId(-1);

        assertThat(uService.addBudget(u1.getId(), b1.getId())).isFalse();
        assertThat(uService.addBudget(u1.getId(), b2.getId())).isFalse();
        assertThat(uService.addBudget(u2.getId(), b1.getId())).isFalse();
        assertThat(uService.addBudget(u2.getId(), b2.getId())).isFalse();
    }

    @Test
    public void removeBudget() {
        User ownerUser = uService.saveUser(ModelTestData.getUser1());
        User connectUser = uService.saveUser(ModelTestData.getUser2());
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        uService.addBudget(ownerUser.getId(), budget.getId());
        uService.addBudget(connectUser.getId(), budget.getId());
        assertThat(uService.findOwner(budget.getId())).isEqualTo(ownerUser);
        assertThat(uService.findConnectUsers(budget.getId()).size() == 1).isTrue();
        assertThat(uService.findConnectUsers(budget.getId()).contains(connectUser)).isTrue();

        uService.removeBudget(ownerUser.getId(), budget.getId());

        assertThat(uService.findOwner(budget.getId())).isNull();
        assertThat(bService.findBudgetById(budget.getId())).isNull();
        assertThat(uService.findOwnBudgets(ownerUser.getId()).contains(budget)).isFalse();
        assertThat(uService.findConnectUsers(budget.getId()).size() == 0).isTrue();
        assertThat(uService.findConnectUsers(budget.getId()).contains(connectUser)).isFalse();
    }

    @Test
    public void removeBudgetWithInvalidIdReturnFalse() {
        User u1 = ModelTestData.getUser1();
        User u2 = ModelTestData.getUser2();
        u1.setId(0);
        u2.setId(-1);

        Budget b1 = ModelTestData.getBudget1();
        Budget b2 = ModelTestData.getBudget2();
        b1.setId(0);
        b2.setId(-1);

        assertThat(uService.removeBudget(u1.getId(), b1.getId())).isFalse();
        assertThat(uService.removeBudget(u1.getId(), b2.getId())).isFalse();
        assertThat(uService.removeBudget(u2.getId(), b1.getId())).isFalse();
        assertThat(uService.removeBudget(u2.getId(), b2.getId())).isFalse();
    }
}