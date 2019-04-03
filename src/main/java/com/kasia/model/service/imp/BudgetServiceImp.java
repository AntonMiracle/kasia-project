package com.kasia.model.service.imp;

import com.kasia.controller.dto.BudgetAdd;
import com.kasia.model.*;
import com.kasia.model.repository.*;
import com.kasia.model.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

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
    @Autowired
    private OperationService operationS;
    @Autowired
    private UserConnectBudgetRepository userConnectBudgetR;
    @Autowired
    private UserConnectBudgetRequestRepository ucbrR;

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
        Optional<BudgetOperation> bo = budgetOperationR.findByBudgetId(budgetId);
        Place place = placeS.findById(placeId);
        if (!bo.isPresent()) return true;
        for (Operation o : bo.get().getOperations()) {
            if (o.getPlace().equals(place)) return false;
        }
        return true;
    }

    @Override
    public boolean isElementCanDeleted(long budgetId, long elementId) {
        Optional<BudgetOperation> bo = budgetOperationR.findByBudgetId(budgetId);
        Element element = elementS.findById(elementId);
        if (!bo.isPresent()) return true;
        for (Operation o : bo.get().getOperations()) {
            if (o.getElement().equals(element)) return false;
        }
        return true;
    }

    @Override
    public Set<Element> findAllElements(long budgetId) {
        Set<Element> result = new HashSet<>();
        budgetElementR.findByBudgetId(budgetId).ifPresent(bp -> result.addAll(bp.getElements()));
        return result;
    }

    @Override
    public Set<Operation> findAllOperations(long budgetId) {
        BudgetOperation bo = budgetOperationR.findByBudgetId(budgetId).orElse(new BudgetOperation());
        return bo.getBudget() == null ? new TreeSet<>() : bo.getOperations();
    }

    @Override
    public boolean addOperation(long budgetId, long operationId) {
        Budget budget = findById(budgetId);
        Operation operation = operationS.findById(operationId);
        BudgetOperation bo = budgetOperationR.findByBudgetId(budgetId).orElse(new BudgetOperation());
        if (operation == null || budget == null) return true;

        if (bo.getBudget() == null) bo.setBudget(budget);
        bo.getOperations().add(operation);
        BigDecimal newAmount = BigDecimal.ZERO;

        if (operation.getType() == OperationType.CONSUMPTION)
            newAmount = budget.getBalance().getAmount().subtract(operation.getPrice());
        else if (operation.getType() == OperationType.INCOME)
            newAmount = budget.getBalance().getAmount().add(operation.getPrice());

        budget.getBalance().setAmount(newAmount);
        save(budget);
        budgetOperationR.save(bo);
        return true;
    }

    @Override
    public boolean removeOperation(long budgetId, long operationId) {
        Budget budget = findById(budgetId);
        Operation operation = operationS.findById(operationId);
        BudgetOperation bo = budgetOperationR.findByBudgetId(budgetId).orElse(new BudgetOperation());
        if (operation == null || budget == null || bo.getBudget() == null) return true;
        if (bo.getOperations().contains(operation)) {
            bo.getOperations().remove(operation);
            BigDecimal newAmount = budget.getBalance().getAmount();
            newAmount = newAmount.subtract(operation.getPrice());
            budget.getBalance().setAmount(newAmount);
            save(budget);
            budgetOperationR.save(bo);
            operationS.delete(operation.getId());
        }
        return true;
    }

    @Override
    public boolean delete(long budgetId, long userId) {

        Optional<BudgetOperation> bo = budgetOperationR.findByBudgetId(budgetId);
        Optional<BudgetElement> be = budgetElementR.findByBudgetId(budgetId);
        Optional<BudgetPlace> bp = budgetPlaceR.findByBudgetId(budgetId);
        Optional<UserBudget> ub = userBudgetR.findByUserId(userId);
        bo.ifPresent(budgetOperation -> budgetOperationR.delete(budgetOperation));
        be.ifPresent(budgetElement -> budgetElementR.delete(budgetElement));
        bp.ifPresent(budgetPlace -> budgetPlaceR.delete(budgetPlace));
        if (ub.isPresent()) {
            UserBudget userBudget = ub.get();
            userBudget.getBudgets().remove(findById(budgetId));
        }
        // add connection remove
        budgetR.delete(findById(budgetId));
        return true;
    }

    @Override
    public boolean connect(long budgetId, long userId) {
        UserConnectBudget ucb = userConnectBudgetR.findByUserId(userId).orElse(new UserConnectBudget());
        if (ucb.getUser() == null) ucb.setUser(userS.findById(userId));
        Budget budget = findById(budgetId);
        if (!ucb.getConnectBudgets().contains(budget)) ucb.getConnectBudgets().add(budget);
// save request to connect
        return true;
    }

    @Override
    public boolean connectRequest(long budgetId, long fromUserId, long toUserId) {
        UserConnectBudgetRequest ucbr = new UserConnectBudgetRequest();
        ucbr.setBudgetId(budgetId);
        ucbr.setFromUserId(fromUserId);
        ucbr.setToUserId(toUserId);
        ucbrR.save(ucbr);
        return true;
    }
}
