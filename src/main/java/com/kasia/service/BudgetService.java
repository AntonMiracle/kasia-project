package com.kasia.service;

import com.kasia.model.Article;
import com.kasia.model.Budget;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

public interface BudgetService extends ValidationService<Budget> {
    Budget create(String name, BigDecimal balance, Currency currency) throws NullPointerException, ValidationException;

    boolean delete(long id) throws IllegalArgumentException;

    boolean update(Budget budget) throws IllegalArgumentException, ValidationException;

    Budget getBudgetById(long id) throws IllegalArgumentException;

    boolean addArticle(Budget budget, Article article) throws NullPointerException, ValidationException;

    boolean removeArticle(Budget budget, Article article) throws NullPointerException, ValidationException;

    Set<Article> getArticlesByType(Budget budget, Article.Type type) throws NullPointerException;
}
