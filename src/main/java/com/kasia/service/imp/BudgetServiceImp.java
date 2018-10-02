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
import java.util.Set;

public class BudgetServiceImp implements BudgetService {
    private BudgetRepository repository;

    public BudgetServiceImp(BudgetRepository repository) {
        this.repository = repository;
    }

    public BudgetServiceImp() {
    }

    public BudgetRepository getRepository() {
        return repository;
    }

    public void setRepository(BudgetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Budget create(String name, BigDecimal balance, Currency currency) throws ValidationException, NullPointerException {
        Budget budget = new Budget();
        budget.setName(name);
        budget.setBalance(balance);
        budget.setCurrency(currency);
        budget.setArticles(new HashSet<>());
        budget.setCreateOn(LocalDateTime.now());

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
        if (budget.getId() == 0) throw new IllegalArgumentException();
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

        Set<Article> newArticles = new HashSet<>(budget.getArticles());
        BigDecimal newBalance = addToBalance(budget, article);
        if (!newArticles.add(article) || !(newBalance != null)) return false;

        budget.setArticles(newArticles);
        budget.setBalance(newBalance);
        return update(budget);
    }


    @Override
    public boolean removeArticle(Budget budget, Article article) throws NullPointerException, ValidationException {
        if (budget == null || article == null) throw new NullPointerException();
        if (!isValid(budget)) throw new ValidationException();

        Set<Article> newArticles = new HashSet<>(budget.getArticles());
        BigDecimal newBalance = removeFromBalance(budget, article);
        if (!newArticles.remove(article) || !(newBalance != null)) return false;

        budget.setArticles(newArticles);
        budget.setBalance(newBalance);
        return update(budget);
    }

    private BigDecimal addToBalance(Budget budget, Article article) throws NullPointerException {
        if (article == null || budget == null) throw new NullPointerException();

        if (article.getType() == Article.Type.INCOME) {
            return budget.getBalance().add(article.getAmount());
        }
        if (article.getType() == Article.Type.CONSUMPTION) {
            return budget.getBalance().subtract(article.getAmount());
        }
        return null;
    }

    private BigDecimal removeFromBalance(Budget budget, Article article) throws NullPointerException {
        if (article == null || budget == null) throw new NullPointerException();

        if (article.getType() == Article.Type.INCOME) {
            return budget.getBalance().subtract(article.getAmount());
        }
        if (article.getType() == Article.Type.CONSUMPTION) {
            return budget.getBalance().add(article.getAmount());
        }
        return null;
    }

    @Override
    public boolean isValid(Budget model) throws NullPointerException {
        if (model == null) throw new NullPointerException();
        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }
}
