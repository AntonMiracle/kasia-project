package com.kasia.model.service.imp;

import com.kasia.controller.dto.BudgetAdd;
import com.kasia.model.*;
import com.kasia.model.repository.BudgetPlaceRepository;
import com.kasia.model.repository.BudgetRepository;
import com.kasia.model.repository.UserBudgetRepository;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.PlaceService;
import com.kasia.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @Autowired
    private PlaceService placeS;
    @Autowired
    private BudgetPlaceRepository budgetPlaceR;

    @Override
    public boolean setOwner(long budgetId, long userId) {
        User owner = userS.findById(userId);
        Budget budget = findById(budgetId);

        if (budget != null && owner != null) {
            UserBudget result = userBudgetR.findByUserId(owner.getId()).orElse(new UserBudget());
            if (result.getUser() == null) result.setUser(owner);
            result.getBudgets().add(budget);
            userBudgetR.save(result);
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

    @Override
    public Budget convert(BudgetAdd budgetAdd) {
        Balance balance = new Balance(new BigDecimal(budgetAdd.getPrice()), budgetAdd.getCurrency(), LocalDateTime.now());
        return new Budget(budgetAdd.getName(), balance, LocalDateTime.now());
    }

    @Override
    public boolean addPlace(long budgetId, long placeId) {
        Place place = placeS.findById(placeId);
        Budget budget = budgetR.findById(budgetId).orElse(null);
        BudgetPlace bp = budgetPlaceR.findByBudgetId(budgetId).orElse(new BudgetPlace());
        if (place == null || budget == null) return false;
        if (bp.getBudget() == null) bp.setBudget(budget);
        if (bp.getPlaces().stream().filter(p -> p.getName().equals(place.getName())).count() == 0)
            bp.getPlaces().add(place);
        budgetPlaceR.save(bp);
        return true;
    }

    @Override
    public Set<Place> findAllPlaces(long budgetId) {
        Set<Place> result = new HashSet<>();
        budgetPlaceR.findByBudgetId(budgetId).ifPresent(bp -> result.addAll(bp.getPlaces()));
        return result;
    }

    @Override
    public boolean isPlaceNameUnique(long budgetId, String name) {
        return findAllPlaces(budgetId).stream().filter(place -> place.getName().equals(name)).count() == 0;
    }
}
