package com.kasia.model.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.UserBudget;
import com.kasia.model.repository.UserBudgetRepository;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.UserBudgetService;
import com.kasia.model.service.UserService;
import com.kasia.model.validation.BudgetValidationService;
import com.kasia.model.validation.UserBudgetValidationService;
import com.kasia.model.validation.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserBudgetServiceImp implements UserBudgetService {
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private BudgetValidationService budgetValidationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidationService userValidationService;
    @Autowired
    private UserBudgetValidationService userBudgetValidationService;
    @Autowired
    private UserBudgetRepository userBudgetRepository;

    @Override
    public UserBudget save(UserBudget model) {
        userBudgetValidationService.verifyValidation(model);
        return userBudgetRepository.save(model);
    }

    @Override
    public boolean delete(UserBudget model) {
        userBudgetValidationService.verifyValidation(model);
        userBudgetValidationService.verifyPositiveId(model.getId());
        userBudgetRepository.delete(model);
        for (Budget budget : model.getBudgets()) {
            budgetService.delete(budget);
            //need to delete BudgetElement
            //need to delete BudgetElementProvider
            //need to delete BudgetOperation
            //need to delete UserConnectBudget
            throw new NotImplementedException();
        }
        return true;
    }

    @Override
    public UserBudget findById(long id) {
        userBudgetValidationService.verifyPositiveId(id);
        Optional<UserBudget> userBudget = userBudgetRepository.findById(id);
        return userBudget.isPresent() ? userBudget.get() : null;
    }

    @Override
    public Set<UserBudget> findAll() {
        Set<UserBudget> userBudgets = new HashSet<>();
        userBudgetRepository.findAll().forEach(userBudgets::add);
        return userBudgets;
    }

    @Override
    public boolean isNameUnique(User user, String budgetName) {
        UserBudget userBudget = findByUserId(user.getId());
        if (userBudget == null) return true;

        for (Budget budget : userBudget.getBudgets()) {
            if (budget.getName().equals(budgetName)) return false;
        }
        return true;
    }

    @Override
    public UserBudget findByUserId(long id) {
        userBudgetValidationService.verifyPositiveId(id);
        Optional<UserBudget> userBudget = userBudgetRepository.findByUserId(id);
        return userBudget.isPresent() ? userBudget.get() : null;
    }

    @Override
    public boolean addBudget(User user, Budget budget) {
        userValidationService.verifyValidation(user);
        userValidationService.verifyPositiveId(user.getId());
        budgetValidationService.verifyValidation(budget);
        if (!isNameUnique(user, budget.getName())) return false;

        UserBudget userBudget = findByUserId(user.getId());
        if (userBudget == null) userBudget = create(user);
        if (userBudget.getBudgets().contains(budget)) return false;

        userBudget.getBudgets().add(budgetService.save(budget));
        save(userBudget);
        return true;
    }

    @Override
    public boolean removeBudget(User user, Budget budget) {
        userValidationService.verifyValidation(user);
        budgetValidationService.verifyValidation(budget);
        userValidationService.verifyPositiveId(user.getId());
        budgetValidationService.verifyPositiveId(budget.getId());

        UserBudget userBudget = findByUserId(user.getId());
        if (userBudget == null || !userBudget.getBudgets().contains(budget)) return true;

        userBudget.getBudgets().remove(budget);
        save(userBudget);
        return true;
    }

    @Override
    public UserBudget create(User user) {
        userValidationService.verifyValidation(user);
        userValidationService.verifyPositiveId(user.getId());
        return new UserBudget(user, new HashSet<>());
    }
}
