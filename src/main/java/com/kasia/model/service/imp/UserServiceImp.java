package com.kasia.model.service.imp;

import com.kasia.model.User;
import com.kasia.model.service.UserService;
import com.kasia.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service
public class UserServiceImp implements UserService, ValidationService<User> {
    @Override
    public User save(User model) {
        return null;
    }

    @Override
    public boolean delete(User model) {
        return false;
    }

    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public User create(String email, String name, String password, ZoneId zoneId) {
        return null;
    }

    @Override
    public boolean isEmailUnique(String email) {
        return false;
    }

    @Override
    public boolean isNameUnique(String name) {
        return false;
    }

    @Override
    public String crypt(String nonCryptPassword) {
        return null;
    }

    @Override
    public ZoneId zoneIdOf(String zoneId) {
        return null;
    }

    @Override
    public User getByName(String name) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }
}
