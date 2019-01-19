package com.kasia.model.service.imp;

import com.kasia.exception.EmailExistRuntimeException;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.exception.LocaleFormatRuntimeException;
import com.kasia.exception.UserNameExistRuntimeException;
import com.kasia.model.Budget;
import com.kasia.model.Role;
import com.kasia.model.User;
import com.kasia.model.repository.UserRepository;
import com.kasia.model.service.UserService;
import com.kasia.model.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    @Override
    public User saveUser(User model) {
        uValidation.verifyValidation(model);
        if (model.getId() == 0) {
            if (!isUserNameUnique(model.getName())) throw new UserNameExistRuntimeException();
            if (!isUserEmailUnique(model.getEmail())) throw new EmailExistRuntimeException();
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
        throw new NotImplementedException();
    }

    @Override
    public boolean activate(User user) {
        throw new NotImplementedException();
    }

    @Override
    public boolean deactivate(User user) {
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }

    @Override
    public Locale getDefaultLocale() {
        return new Locale("pl", "PL");
    }

    @Override
    public Set<Budget> findAllOwnBudget(User user) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addBudget(User user, Budget budget) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeBudget(User user, Budget budget) {
        throw new NotImplementedException();
    }

    @Override
    public Set<User> findAllConnectedUser(Budget budget) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isUserOwnerOfBudget(Budget budget, User user) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isUserConnectedToBudget(Budget budget, User user) {
        throw new NotImplementedException();
    }

}
