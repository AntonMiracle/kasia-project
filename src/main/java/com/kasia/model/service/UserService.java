package com.kasia.model.service;

import com.kasia.controller.dto.Registration;
import com.kasia.model.Budget;
import com.kasia.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

public interface UserService extends UserDetailsService, PasswordEncoder {
    User findByEmail(String email);

    User findById(long id);

    String cryptPassword(String nonCryptPassword);

    User convert(Registration registration);

    User save(User user);

    Set<Budget> findOwnBudgets(long userId);

    boolean isEmailUnique(String email);
}
