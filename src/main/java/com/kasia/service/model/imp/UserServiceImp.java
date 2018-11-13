package com.kasia.service.model.imp;

import com.kasia.model.Article;
import com.kasia.model.User;
import com.kasia.repository.UserRepository;
import com.kasia.service.model.UserService;
import com.kasia.service.validation.UserValidationService;

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
    @Inject
    private UserRepository repository;
    @Inject
    private UserValidationService validationService;

    @Override
    public User create(String email, String password, String nick, ZoneId zoneId) throws NullPointerException, ValidationException {
        email = email.trim();
        nick = nick.trim();
        password = cryptPassword(password.trim());
        Set<User.Role> roles = new HashSet<>();
        roles.add(User.Role.USER);
        User user = new User(roles, email, nick, password, zoneId, LocalDateTime.now().withNano(0));
        user.setBudgets(new HashSet<>());
        user.setEmployers(new HashSet<>());
        user.setArticles(new HashSet<>());
        if (!validationService.isValid(user)) throw new ValidationException();
        return repository.save(user);
    }

    @Override
    public User update(User user) throws ValidationException, NullPointerException, IllegalArgumentException {
        if (!validationService.isValid(user)) throw new ValidationException();
        if (user.getId() == 0) throw new IllegalArgumentException();
        repository.save(user);
        return repository.getById(user.getId());
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        User user = repository.getById(id);
        return user == null || repository.delete(user);
    }

    @Override
    public User getUserById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return repository.getById(id);
    }

    @Override
    public User getByEmail(String email) throws NullPointerException, ValidationException {
        if (email == null) throw new NullPointerException();
        if (!validationService.isEmailValid(email)) throw new ValidationException();
        return repository.getByEmail(email.trim());
    }

    @Override
    public User getByNick(String nick) throws NullPointerException, ValidationException {
        if (nick == null) throw new NullPointerException();
        if (!validationService.isNickValid(nick)) throw new ValidationException();
        return repository.getByNick(nick.trim());
    }

    @Override
    public String cryptPassword(String password) throws NullPointerException, ValidationException {
        if (password == null) throw new NullPointerException();
        if (!validationService.isNonCryptPasswordValid(password)) throw new ValidationException();

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
        for (Article article : repository.getById(user.getId()).getArticles()) {
            if (article.getType() == type) {
                result.add(article);
            }
        }
        return result;
    }

    @Override
    public Set<User> getAllUsers() {
        return repository.getAll();
    }

}
