package com.kasia.model.service.imp;

import com.kasia.controller.dto.BudgetAdd;
import com.kasia.model.*;
import com.kasia.model.repository.*;
import com.kasia.model.service.BudgetService;
import com.kasia.model.service.ElementService;
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
    @Autowired
    private BudgetOperationRepository budgetOperationR;
    @Autowired
    private BudgetElementRepository budgetElementR;
    @Autowired
    private ElementService elementS;

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
    public boolean removePlace(long budgetId, long placeId) {
        Place place = placeS.findById(placeId);
        Budget budget = findById(budgetId);
        BudgetPlace bp = budgetPlaceR.findByBudgetId(budgetId).orElse(new BudgetPlace());
        if (place == null || budget == null) return true;
        if (bp.getPlaces().size() == 0) placeS.delete(placeId);
        else if (bp.getPlaces().contains(place)) {
            bp.getPlaces().remove(place);
            budgetPlaceR.save(bp);
            placeS.delete(place.getId());
        }
        return true;
    }

    @Override
    public boolean removeElement(long budgetId, long elementId) {
        Element element = elementS.findById(elementId);
        Budget budget = findById(budgetId);
        BudgetElement be = budgetElementR.findByBudgetId(budgetId).orElse(new BudgetElement());
        if (element == null || budget == null) return true;
        if (be.getElements().size() == 0) elementS.delete(elementId);
        else if (be.getElements().contains(element)) {
            be.getElements().remove(element);
            budgetElementR.save(be);
            elementS.delete(element.getId());
        }
        return true;
    }

    @Override
    public boolean addElement(long budgetId, long elementId) {
        Element element = elementS.findById(elementId);
        Budget budget = findById(budgetId);
        BudgetElement be = budgetElementR.findByBudgetId(budgetId).orElse(new BudgetElement());
        if (budget == null || element == null) return false;
        if (be.getBudget() == null) be.setBudget(budget);
        if (be.getElements().stream().filter(e -> e.getName().equals(element.getName())).count() == 0)
            be.getElements().add(element);
        budgetElementR.save(be);
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

    @Override
    public boolean isElementNameUnique(long budgetId, String name) {
        return findAllElements(budgetId).stream().filter(e -> e.getName().equals(name)).count() == 0;
    }

    @Override
    public boolean isOwner(long budgetId, long userId) {
        Optional<UserBudget> result = userBudgetR.findByUserId(userId);
        return result.filter(userBudget -> userBudget.getBudgets().stream().filter(budget -> budget.getId() == budgetId).count() == 1).isPresent();
    }

    @Override
    public boolean isPlaceCanDeleted(long budgetId, long placeId) {
        Optional<BudgetOperation> budgetOperation = budgetOperationR.findByBudgetId(budgetId);
        return !budgetOperation.filter(
                bo -> bo.getOperations().stream().filter(
                        operation -> operation.getPlace().getId() == placeId).count() > 0)
                .isPresent();
    }

    @Override
    public boolean isElementCanDeleted(long budgetId, long elementId) {
        Optional<BudgetOperation> budgetOperation = budgetOperationR.findByBudgetId(budgetId);
        return !budgetOperation.filter(
                bo -> bo.getOperations().stream().filter(
                        operation -> operation.getElement().getId() == elementId).count() > 0)
                .isPresent();
    }

    @Override
    public Set<Element> findAllElements(long budgetId) {
        Set<Element> result = new HashSet<>();
        budgetElementR.findByBudgetId(budgetId).ifPresent(bp -> result.addAll(bp.getElements()));
        return result;
    }
}
