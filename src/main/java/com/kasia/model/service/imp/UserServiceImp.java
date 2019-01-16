package com.kasia.model.service.imp;

import com.kasia.exception.*;
import com.kasia.model.Role;
import com.kasia.model.User;
import com.kasia.model.repository.UserRepository;
import com.kasia.model.service.UserService;
import com.kasia.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class UserServiceImp implements UserService, ValidationService<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User model) {
        if (model.getId() == 0) {
            if (!isNameUnique(model.getName())) throw new UserNameExistRuntimeException();
            if (!isEmailUnique(model.getEmail())) throw new EmailExistRuntimeException();
            if (!activate(model)) throw new UserActivatedRuntimeException();
            model.setPassword(crypt(model.getPassword()));
        }
        return userRepository.save(model);
    }

    @Override
    public boolean delete(User model) {
        if (model.getId() <= 0) throw new IdRuntimeException();
        userRepository.delete(model);
        return true;
    }

    @Override
    public User findById(long id) {
        if (id <= 0) throw new IdRuntimeException();
        Optional<User> user = userRepository.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public Set<User> findAll() {
        Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User create(String email, String name, String password, ZoneId zoneId, Locale locale) {
        password = crypt(password);
        return new User(email, name, password, zoneId, LocalDateTime.now().withNano(0), Role.USER, false, locale);
    }

    @Override
    public boolean isEmailUnique(String email) {
        return !userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isNameUnique(String name) {
        return !userRepository.findByName(name).isPresent();
    }

    @Override
    public String crypt(String nonCryptPassword) {
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
    public User findByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public boolean isActivated(User user) {
        return user.isActivated();
    }

    @Override
    public boolean activate(User user) {
        user.setActivated(true);
        return true;
    }

    @Override
    public boolean deactivate(User user) {
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
    public Locale getDefaultLocale() {
        return new Locale("pl", "PL");
    }

}
