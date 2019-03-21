package com.kasia.model.service.imp;

import com.kasia.model.Budget;
import com.kasia.model.User;
import com.kasia.model.UserBudget;
import com.kasia.model.repository.BudgetRepository;
import com.kasia.model.repository.UserBudgetRepository;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class BudgetServiceImp implements BudgetService {
    @Autowired
    private UserService userS;
    @Autowired
    private BudgetRepository budgetR;
    @Autowired
    private UserBudgetRepository userBudgetR;

    @Override
    public boolean setOwner(long budgetId, long userId) {
        User owner = userS.findById(userId);
        Budget budget = findById(budgetId);

        if (budget != null && owner != null) {
            UserBudget result = userBudgetR.findByUserId(owner.getId()).orElse(new UserBudget());
            if (result.getUser() == null) result.setUser(owner);
            result.getBudgets().add(budget);
            userBudgetR.save(result);
            System.out.println(result);
            return true;
        }

        return false;
    }

    @Override
    public Set<Budget> findOwnBudgets(long userId) {
        Set<Budget> result = new HashSet<>();
        Optional<UserBudget> userBudget = userBudgetR.findByUserId(userId);
        userBudget.ifPresent(userBudget1 -> userBudget1.getBudgets().forEach(result::add));
        return result;
    }

    @Override
    public Budget findById(long budgetId) {
        return budgetR.findById(budgetId).orElse(null);
    }

    @Override
    public boolean isOwnBudgetNameUnique(long userId, String name) {
        Set<Budget> ownBudgets = findOwnBudgets(userId);
        return ownBudgets.stream().filter(b -> b.getName().equals(name)).count() == 0;
    }

    @Override
    public Budget save(Budget budget) {
        return budgetR.save(budget);
    }
}
