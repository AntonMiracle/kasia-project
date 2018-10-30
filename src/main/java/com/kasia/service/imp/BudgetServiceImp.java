package com.kasia.service.imp;

import com.kasia.model.Article;
import com.kasia.model.Budget;
import com.kasia.repository.BudgetRepository;
import com.kasia.service.BudgetService;

import javax.ejb.EJB;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

public class BudgetServiceImp implements BudgetService {
    @EJB
    private BudgetRepository budgetRepository;

    @Override
    public Budget create(String name, BigDecimal balance, Currency currency) throws ValidationException, NullPointerException {
        Budget budget = new Budget(name, balance, currency, LocalDateTime.now().withNano(0));
        budget.setArticles(new HashSet<>());
        if (!isValid(budget)) throw new ValidationException();
        budgetRepository.save(budget);
        return budgetRepository.getById(budget.getId());
    }

    @Override
    public boolean delete(long id) throws IllegalArgumentException {
        Budget budget = budgetRepository.getById(id);
        if (budget == null) return true;
        return budgetRepository.delete(budget);
    }

    @Override
    public Budget update(Budget budget) throws IllegalArgumentException, ValidationException {
        if (!(isValid(budget))) throw new ValidationException();
        if (budget.getId() == 0) throw new IllegalArgumentException();
        budgetRepository.save(budget);
        return budgetRepository.getById(budget.getId());
    }


    @Override
    public Budget getBudgetById(long id) throws IllegalArgumentException {
        if (id <= 0) throw new IllegalArgumentException();
        return budgetRepository.getById(id);
    }

    @Override
    public Budget addArticle(Budget budget, Article article) throws NullPointerException, ValidationException {
        if (budget == null || article == null) throw new NullPointerException();
        if (!isValid(budget)) throw new ValidationException();

        Set<Article> newArticles = new HashSet<>(budget.getArticles());
        BigDecimal newBalance = addToBalance(budget, article);
        if (!newArticles.add(article) || newBalance == null) return null;

        budget.setArticles(newArticles);
        budget.setBalance(newBalance);
        budgetRepository.save(budget);
        return budgetRepository.getById(budget.getId());
    }


    @Override
    public Budget removeArticle(Budget budget, Article article) throws NullPointerException, ValidationException {
        if (budget == null || article == null) throw new NullPointerException();
        if (!isValid(budget)) throw new ValidationException();

        Set<Article> newArticles = new HashSet<>(budget.getArticles());
        BigDecimal newBalance = removeFromBalance(budget, article);
        if (!newArticles.remove(article) || newBalance == null) return null;

        budget.setArticles(newArticles);
        budget.setBalance(newBalance);
        budgetRepository.save(budget);
        return budgetRepository.getById(budget.getId());
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
    public Set<Article> getArticlesByType(Budget budget, Article.Type type) throws NullPointerException {
        if (budget == null || type == null) throw new NullPointerException();
        Set<Article> result = new HashSet<>();
        for (Article article : budget.getArticles()) {
            if (article.getType() == type) {
                result.add(article);
            }
        }
        return result;
    }

    @Override
    public Set<Budget> getAllBudgets() {
        return budgetRepository.getAll();
    }

    @Override
    public boolean isValid(Budget model) throws NullPointerException {
        if (model == null) throw new NullPointerException();
        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }
}
