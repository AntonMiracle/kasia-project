package com.kasia.model.service.imp;

import com.kasia.exception.EmailExistRuntimeException;
import com.kasia.exception.IdRuntimeException;
import com.kasia.exception.UserActivatedRuntimeException;
import com.kasia.exception.UserNameExistRuntimeException;
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
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, ValidationService<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User model) {
        if (model.getId() == 0 && !isNameUnique(model.getName())) throw new UserNameExistRuntimeException();
        if (model.getId() == 0 && !isEmailUnique(model.getEmail())) throw new EmailExistRuntimeException();
        if (model.getId() == 0 && !activate(model)) throw new UserActivatedRuntimeException();
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
    public User create(String email, String name, String password, ZoneId zoneId) {
        password = crypt(password);
        return new User(email, name, password, zoneId, LocalDateTime.now().withNano(0), Role.USER, false);
    }

    @Override
    public boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email).isPresent() ? false : true;
    }

    @Override
    public boolean isNameUnique(String name) {
        return userRepository.findByName(name).isPresent() ? false : true;
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

}
