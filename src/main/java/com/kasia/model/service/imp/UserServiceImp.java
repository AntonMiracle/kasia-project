package com.kasia.model.service.imp;

import com.kasia.exception.EmailExistRuntimeException;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.exception.LocaleFormatRuntimeException;
import com.kasia.exception.UserNameExistRuntimeException;
import com.kasia.model.*;
import com.kasia.model.repository.UserBudgetRepository;
import com.kasia.model.repository.UserConnectBudgetRepository;
import com.kasia.model.repository.UserRepository;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.UserService;
import com.kasia.model.validation.BudgetValidation;
import com.kasia.model.validation.UserBudgetValidation;
import com.kasia.model.validation.UserConnectBudgetValidation;
import com.kasia.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository uRepository;
    @Autowired
    private UserValidation uValidation;
    @Autowired
    private UserBudgetRepository ubRepository;
    @Autowired
    private UserBudgetValidation ubValidation;
    @Autowired
    private UserConnectBudgetRepository ucbRepository;
    @Autowired
    private UserConnectBudgetValidation ucbValidation;
    @Autowired
    private BudgetService bService;
    @Autowired
    private BudgetValidation bValidation;

    @Override
    public User saveUser(User model) {
        uValidation.verifyValidation(model);
        if (model.getId() == 0) {
            if (!isUserNameUnique(model.getName())) throw new UserNameExistRuntimeException();
            if (!isUserEmailUnique(model.getEmail())) throw new EmailExistRuntimeException();
            activate(model);
            return uRepository.save(model);
        }
        if (model.getId() > 0) {
            User oldUser = findUserById(model.getId());
            if (!oldUser.getName().equals(model.getName()) && !isUserNameUnique(model.getName()))
                model.setName(oldUser.getName());
            if (!oldUser.getEmail().equals(model.getEmail()) && !isUserEmailUnique(model.getEmail()))
                model.setEmail(oldUser.getEmail());
            return uRepository.save(model);
        }
        throw new IdInvalidRuntimeException();
    }

    @Override
    public boolean deleteUser(User model) {
        uValidation.verifyValidation(model);
        uValidation.verifyPositiveId(model.getId());
        uRepository.delete(model);
        return true;
    }

    @Override
    public User findUserById(long id) {
        uValidation.verifyPositiveId(id);
        Optional<User> user = uRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public Set<User> findAllUsers() {
        Set<User> users = new HashSet<>();
        uRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User createUser(String email, String name, String password, ZoneId zoneId, Locale locale) {
        User user = new User(email, name, password, zoneId, LocalDateTime.now().withNano(0), Role.USER, false, locale);
        uValidation.verifyValidation(user);
        return user;
    }

    @Override
    public boolean isUserEmailUnique(String email) {
        return findUserByEmail(email) == null;
    }

    @Override
    public boolean isUserNameUnique(String name) {
        return findUserByName(name) == null;
    }

    @Override
    public String cryptPassword(String nonCryptPassword) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(nonCryptPassword.getBytes(), 0, nonCryptPassword.length());
        return new BigInteger(1, md5.digest()).toString(16) + "0Aa";
    }

    @Override
    public ZoneId zoneIdOf(String zoneId) {
        try {
            return ZoneId.of(zoneId);
        } catch (DateTimeException ex) {
            return ZoneId.systemDefault();
        }
    }

    @Override
    public Locale localeOf(String lang, String country) {
        Locale locale = new Locale(lang, country);
        if (getCorrectAvailableLocales().contains(locale)) return locale;
        locale = getDefaultLocale();
        if (getCorrectAvailableLocales().contains(locale)) return locale;
        throw new LocaleFormatRuntimeException();
    }

    @Override
    public User findUserByName(String name) {
        return uRepository.findByName(name).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return uRepository.findByEmail(email).orElse(null);
    }

    @Override
    public boolean isActivated(User user) {
        uValidation.verifyValidation(user);
        return user.isActivated();
    }

    @Override
    public boolean activate(User user) {
        uValidation.verifyValidation(user);
        user.setActivated(true);
        return true;
    }

    @Override
    public boolean deactivate(User user) {
        uValidation.verifyValidation(user);
        user.setActivated(false);
        return true;
    }

    @Override
    public Set<Locale> getCorrectAvailableLocales() {
        Set<Locale> locales = new HashSet<>();
        for (Locale locale : Locale.getAvailableLocales()) {
            if (locale.getLanguage().length() > 0 && locale.getCountry().length() > 0)
                locales.add(locale);
        }
        return locales;
    }

    @Override
    public boolean isLocaleAvailable(Locale locale) {
        return getCorrectAvailableLocales().contains(locale);
    }

    @Override
    public Locale getDefaultLocale() {
        return new Locale("pl", "PL");
    }

    @Override
    public Set<Budget> findOwnBudgets(User user) {
        uValidation.verifyPositiveId(user.getId());
        Optional<UserBudget> optional = ubRepository.findByUserId(user.getId());
        return optional.map(UserBudget::getBudgets).orElseGet(HashSet::new);
    }

    @Override
    public User findOwner(Budget budget) {
        budget = bService.findBudgetById(budget.getId());

        Set<UserBudget> ub = new HashSet<>();
        ubRepository.findAll().forEach(ub::add);

        for (UserBudget userBudget : ub) {
            if (userBudget.getBudgets().contains(budget)) {
                return userBudget.getUser();
            }
        }
        return null;
    }

    @Override
    public boolean addBudget(User user, Budget budget) {
        user = findUserById(user.getId());
        budget = bService.findBudgetById(budget.getId());
        User owner = findOwner(budget);

        if (owner == null) {
            UserBudget ub = new UserBudget();
            ub.setUser(user);
            ub.setBudgets(new HashSet<>());
            ub.getBudgets().add(budget);
            ubValidation.verifyValidation(ub);
            ubRepository.save(ub);
            return true;
        }
        if (owner.equals(user)) return false;

        Optional<UserConnectBudget> optionalUCB = ucbRepository.findByUserId(user.getId());
        if (optionalUCB.isPresent()) {
            UserConnectBudget ucb = optionalUCB.get();
            if (ucb.getConnectBudgets().contains(budget)) return false;
            ucb.getConnectBudgets().add(budget);
            ucbValidation.verifyValidation(ucb);
            ucbRepository.save(ucb);
            return true;
        } else {
            UserConnectBudget newUCB = new UserConnectBudget(user, new HashSet<>());
            newUCB.getConnectBudgets().add(budget);
            ucbValidation.verifyValidation(newUCB);
            ucbRepository.save(newUCB);
            return true;
        }
    }

    @Override
    public boolean removeBudget(User user, Budget budget) {
        user = findUserById(user.getId());
        budget = bService.findBudgetById(budget.getId());
        User owner = findOwner(budget);

        if (owner.equals(user)) {
            Optional<UserBudget> optionalUB = ubRepository.findByUserId(user.getId());
            if (optionalUB.isPresent() && optionalUB.get().getBudgets().contains(budget)) {
                UserBudget ub = optionalUB.get();
                ub.getBudgets().remove(budget);
                ubValidation.verifyValidation(ub);
                bService.deleteBudget(budget);
                ubRepository.save(ub);
                return true;
            }
            return false;
        }

        Optional<UserConnectBudget> optionalUCB = ucbRepository.findByUserId(user.getId());
        if (optionalUCB.isPresent() && optionalUCB.get().getConnectBudgets().contains(budget)) {
            UserConnectBudget ucb = optionalUCB.get();
            ucb.getConnectBudgets().remove(budget);
            ucbValidation.verifyValidation(ucb);
            ucbRepository.save(ucb);
            return true;
        }
        return false;
    }

    @Override
    public Set<User> findConnectUsers(Budget budget) {
        budget = bService.findBudgetById(budget.getId());

        Set<UserConnectBudget> ucb = new HashSet<>();
        ucbRepository.findAll().forEach(ucb::add);

        Set<User> users = new HashSet<>();
        for (UserConnectBudget userConnectBudget : ucb) {
            if (userConnectBudget.getConnectBudgets().contains(budget)) users.add(userConnectBudget.getUser());
        }

        return users;
    }

    @Override
    public boolean isUserOwnBudget(Budget budget, User user) {
        uValidation.verifyPositiveId(user.getId());
        bValidation.verifyValidation(budget);
        bValidation.verifyPositiveId(budget.getId());

        UserBudget ub = ubRepository.findByUserId(user.getId()).orElse(null);
        return !(ub == null || ub.getBudgets() == null) && ub.getBudgets().contains(budget);
    }

    @Override
    public boolean isUserConnectToBudget(Budget budget, User user) {
        uValidation.verifyPositiveId(user.getId());
        bValidation.verifyValidation(budget);
        bValidation.verifyPositiveId(budget.getId());

        UserConnectBudget ucb = ucbRepository.findByUserId(user.getId()).orElse(null);
        return !(ucb == null || ucb.getConnectBudgets() == null) && ucb.getConnectBudgets().contains(budget);
    }

}
