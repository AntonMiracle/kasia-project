package com.kasia.service.imp;

import com.kasia.model.Article;
import com.kasia.model.Budget;
import com.kasia.repository.BudgetRepository;
import com.kasia.service.BudgetService;

import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;

public class BudgetServiceImp implements BudgetService {
    private BudgetRepository repository;

    @Override
    public Budget create(String name, BigDecimal balance, Currency currency) throws ValidationException {
        Budget budget = new Budget();
        budget.setName(name);
        budget.setBalance(balance);
        budget.setCurrency(currency);
        budget.setArticles(new HashSet<>());
        budget.setStartOn(LocalDateTime.now());

        if (!isValid(budget)) throw new ValidationException();

        return repository.save(budget);
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        Budget budget = getBudgetById(id);
        if (budget == null) return true;
        return repository.delete(budget);
    }

    @Override
    public boolean update(Budget budget) throws IllegalArgumentException, ValidationException {
        if (!(isValid(budget))) throw new ValidationException();
        if (budget.getId() <= 0) throw new IllegalArgumentException();
        return repository.update(budget);
    }


    @Override
    public Budget getBudgetById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return repository.getById(id);
    }

    @Override
    public boolean addArticle(Budget budget, Article article) throws NullPointerException, ValidationException {
        if (budget == null || article == null) throw new NullPointerException();
        if (!isValid(budget)) throw new ValidationException();
        if (!budget.getArticles().add(article) || !addToBalance(budget, article)) return false;
        return update(budget);
    }

    @Override
    public boolean removeArticle(Budget budget, Article article) throws NullPointerException, ValidationException {
        if (budget == null || article == null) throw new NullPointerException();
        if (!isValid(budget)) throw new ValidationException();
        if (!budget.getArticles().remove(article) || !removeFromBalance(budget, article)) return false;
        return update(budget);
    }

    private boolean addToBalance(Budget budget, Article article) throws NullPointerException {
        if (article == null || budget == null) throw new NullPointerException();

        if (article.getType() == Article.Type.INCOME) {
            BigDecimal newBalance = budget.getBalance().add(article.getAmount());
            budget.setBalance(newBalance);
            return true;
        }
        if (article.getType() == Article.Type.CONSUMPTION) {
            BigDecimal newBalance = budget.getBalance().subtract(article.getAmount());
            budget.setBalance(newBalance);
            return true;
        }
        return false;
    }

    private boolean removeFromBalance(Budget budget, Article article) throws NullPointerException {
        if (article == null || budget == null) throw new NullPointerException();

        if (article.getType() == Article.Type.INCOME) {
            BigDecimal newBalance = budget.getBalance().subtract(article.getAmount());
            budget.setBalance(newBalance);
            return true;
        }
        if (article.getType() == Article.Type.CONSUMPTION) {
            BigDecimal newBalance = budget.getBalance().add(article.getAmount());
            budget.setBalance(newBalance);
            return true;
        }
        return false;
    }

    @Override
    public boolean isValid(Budget model) throws NullPointerException {
        if (model == null) throw new NullPointerException();
        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }
}
