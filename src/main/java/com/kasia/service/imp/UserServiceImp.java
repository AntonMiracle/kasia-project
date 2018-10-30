package com.kasia.service.imp;

import com.kasia.model.Economy;
import com.kasia.model.User;
import com.kasia.repository.UserRepository;
import com.kasia.service.UserService;
import com.kasia.validation.user.UserValidation;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

public class UserServiceImp implements UserService {
    @EJB
    private UserRepository userRepository;

    @Override
    public User create(String email, String password, String nick, ZoneId zoneId) throws NullPointerException, ValidationException {
        email = email.trim();
        nick = nick.trim();
        password = cryptPassword(password.trim());
        User user = new User(User.Role.USER, email, nick, password, zoneId, LocalDateTime.now().withNano(0));
        user.setEconomies(new HashSet<>());
        if (!isValid(user)) throw new ValidationException();
        return userRepository.save(user);
    }

    @Override
    public User update(User user) throws ValidationException, NullPointerException, IllegalArgumentException {
        if (!isValid(user)) throw new ValidationException();
        if (user.getId() == 0) throw new IllegalArgumentException();
        userRepository.save(user);
        return userRepository.getById(user.getId());
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        User user = userRepository.getById(id);
        if (user == null) return true;
        return userRepository.delete(user);
    }

    @Override
    public User getUserById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return userRepository.getById(id);
    }

    @Override
    public User getByEmail(String email) throws NullPointerException, ValidationException {
        if (email == null) throw new NullPointerException();
        if (!new UserValidation().isEmailValid(email)) throw new ValidationException();
        return userRepository.getByEmail(email.trim());
    }

    @Override
    public User getByNick(String nick) throws NullPointerException, ValidationException {
        if (nick == null) throw new NullPointerException();
        if (!new UserValidation().isNickValid(nick)) throw new ValidationException();
        return userRepository.getByNick(nick.trim());
    }

    @Override
    public User addEconomic(User user, Economy economy) throws NullPointerException, ValidationException, IllegalArgumentException {
        if (user == null || economy == null) throw new NullPointerException();
        if (!isValid(user)) throw new ValidationException();

        Set<Economy> newEconomies = user.getEconomies();
        if (!newEconomies.add(economy)) return null;

        user.setEconomies(newEconomies);
        userRepository.save(user);
        return userRepository.getById(user.getId());
    }

    @Override
    public User removeEconomic(User user, Economy economy) throws NullPointerException, ValidationException, IllegalArgumentException {
        if (user == null || economy == null) throw new NullPointerException();
        if (economy.getId() <= 0) throw new IllegalArgumentException();
        if (!isValid(user)) throw new ValidationException();

        Set<Economy> newEconomies = user.getEconomies();
        if (!newEconomies.remove(economy)) return null;

        user.setEconomies(newEconomies);
        userRepository.save(user);
        return userRepository.getById(user.getId());
    }

    @Override
    public String cryptPassword(String password) throws NullPointerException, ValidationException {
        if (password == null) throw new NullPointerException();
        if (!new UserValidation().isNonCryptPasswordValid(password)) throw new ValidationException();

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(password.getBytes(), 0, password.length());
        return new BigInteger(1, md5.digest()).toString(16);
    }

    @Override
    public Set<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public boolean isValid(User user) throws NullPointerException {
        if (user == null) throw new NullPointerException();

        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(user).size() == 0;
        }
    }
}
