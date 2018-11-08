package com.kasia.service.imp;

import com.kasia.model.Article;
import com.kasia.model.User;
import com.kasia.repository.UserRepository;
import com.kasia.service.UserService;
import com.kasia.service.ValidationService;
import com.kasia.validation.user.UserValidation;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.ValidationException;
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
    @Inject
    private ValidationService<User> validationService;

    @Override
    public User create(String email, String password, String nick, ZoneId zoneId) throws NullPointerException, ValidationException {
        email = email.trim();
        nick = nick.trim();
        password = cryptPassword(password.trim());
        User user = new User(User.Role.USER, email, nick, password, zoneId, LocalDateTime.now().withNano(0));
        user.setBudgets(new HashSet<>());
        user.setEmployers(new HashSet<>());
        user.setArticles(new HashSet<>());
        if (!validationService.isValid(user)) throw new ValidationException();
        return userRepository.save(user);
    }

    @Override
    public User update(User user) throws ValidationException, NullPointerException, IllegalArgumentException {
        if (!validationService.isValid(user)) throw new ValidationException();
        if (user.getId() == 0) throw new IllegalArgumentException();
        userRepository.save(user);
        return userRepository.getById(user.getId());
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        User user = userRepository.getById(id);
        return user == null || userRepository.delete(user);
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
    public Set<Article> getArticlesByType(User user, Article.Type type) throws NullPointerException {
        if (user == null || type == null) throw new NullPointerException();
        Set<Article> result = new HashSet<>();
        for (Article article : userRepository.getById(user.getId()).getArticles()) {
            if (article.getType() == type) {
                result.add(article);
            }
        }
        return result;
    }
    @Override
    public Set<User> getAllUsers() {
        return userRepository.getAll();
    }

}
