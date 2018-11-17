package com.kasia.model.service;

import com.kasia.model.Article;
import com.kasia.model.User;

import javax.validation.ValidationException;
import java.time.ZoneId;
import java.util.Set;

public interface UserModelService extends ModelService {
    User create(String email, String password, String nick, ZoneId zoneId) throws NullPointerException, ValidationException;

    User update(User user) throws ValidationException, NullPointerException, IllegalArgumentException;

    boolean delete(long id) throws IllegalArgumentException;

    User getUserById(long id) throws IllegalArgumentException;

    User getByEmail(String email) throws NullPointerException, ValidationException;

    User getByNick(String nick) throws NullPointerException, ValidationException;

    String cryptPassword(String password) throws NullPointerException, ValidationException;

    Set<Article> getArticlesByType(User user, Article.Type type) throws NullPointerException;

    Set<User> getAllUsers();
}
