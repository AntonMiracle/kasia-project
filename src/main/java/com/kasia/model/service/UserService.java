package com.kasia.model.service;

import com.kasia.controller.dto.Registration;
import com.kasia.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService extends UserDetailsService, PasswordEncoder {
    User findByEmail(String email);

    User findById(long userId);

    String cryptPassword(String nonCryptPassword);

    User convert(Registration registration);

    User save(User user);

    boolean isEmailUnique(String email);
}
