package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.model.*;
import com.kasia.model.repository.BudgetOperationRepository;
import com.kasia.model.repository.ElementProviderRepository;
import com.kasia.model.repository.ElementRepository;
import com.kasia.model.repository.OperationRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationServiceIT {
    @Autowired
    private BudgetService bService;
    @Autowired
    private ElementRepository eRepository;
    @Autowired
    private ElementProviderRepository epRepository;
    @Autowired
    private UserService uService;
    @Autowired
    private OperationRepository oRepository;
    @Autowired
    private BudgetOperationRepository boRepository;
    @Autowired
    private OperationService oService;

    @After
    public void cleanData() {
        boRepository.findAll().forEach(boRepository::delete);

        oRepository.findAll().forEach(oRepository::delete);
        bService.findAllBudgets().forEach(budget -> bService.deleteBudget(budget.getId()));
        eRepository.findAll().forEach(eRepository::delete);
        epRepository.findAll().forEach(epRepository::delete);
        uService.findAllUsers().forEach(user -> uService.deleteUser(user.getId()));
    }

    private Operation getValidOperationWithNestedPositiveId() {
        Operation operation = ModelTestData.getOperation1();
        operation.getProvider().setId(1);
        operation.getElement().setId(1);
        operation.getUser().setId(1);
        return operation;
    }

    @Test
    public void createOperation() {
        Operation op = getValidOperationWithNestedPositiveId();
        Operation actual = oService.createOperation(op.getUser(), op.getElement(), op.getProvider(), op.getPrice());

        assertThat(actual.getUser()).isEqualTo(op.getUser());
        assertThat(actual.getPrice()).isEqualTo(op.getPrice());
        assertThat(actual.getElement()).isEqualTo(op.getElement());
        assertThat(actual.getProvider()).isEqualTo(op.getProvider());
        assertThat(actual.getCreateOn().compareTo(LocalDateTime.now().plusSeconds(3)) < 0).isTrue();
    }

    @Test
    public void addOperation() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());
        Operation op2 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());

        assertThat(oService.findAllOperations(savedBudget.getId()).size() == 0).isTrue();

        oService.addOperation(savedBudget.getId(), op1);
        assertThat(oService.findAllOperations(savedBudget.getId()).size() == 1).isTrue();

        oService.addOperation(savedBudget.getId(), op2);
        assertThat(oService.findAllOperations(savedBudget.getId()).size() == 2).isTrue();

        assertThat(oService.addOperation(0, null)).isFalse();
        assertThat(oService.addOperation(-1, null)).isFalse();
    }

    @Test
    public void addOperationUpdateBudgetBalance() {
        Budget budget = ModelTestData.getBudget1();
        budget.getBalance().setCurrencies(Currencies.EUR);
        budget.getBalance().setAmount(BigDecimal.ZERO);
        budget = bService.saveBudget(budget);

        Price incomePrice = ModelTestData.getPrice1();
        incomePrice.setCurrencies(Currencies.EUR);
        incomePrice.setAmount(BigDecimal.valueOf(0.5));

        Price consumptionPrice = ModelTestData.getPrice1();
        consumptionPrice.setCurrencies(Currencies.EUR);
        consumptionPrice.setAmount(BigDecimal.valueOf(3));

        Element incomeEl = ModelTestData.getElement1();
        incomeEl.setType(ElementType.INCOME);
        incomeEl = eRepository.save(incomeEl);

        Element consumptionEl = ModelTestData.getElement1();
        consumptionEl.setType(ElementType.CONSUMPTION);
        consumptionEl = eRepository.save(consumptionEl);

        User user = uService.saveUser(ModelTestData.getUser1());
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());

        Operation incomeOp = oService.createOperation(user, incomeEl, provider, incomePrice);
        Operation consumptionOp = oService.createOperation(user, consumptionEl, provider, consumptionPrice);

        oService.addOperation(budget.getId(), incomeOp);
        budget = bService.findBudgetById(budget.getId());
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(0.5));

        oService.addOperation(budget.getId(), consumptionOp);
        budget = bService.findBudgetById(budget.getId());
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(-2.5));
    }

    private Operation getSavedOperationForCheckRuntimeException() {
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());
        return oService.createOperation(user, element, provider, ModelTestData.getPrice1());
    }

    @Test
    public void removeOperation() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());
        oService.addOperation(savedBudget.getId(), op1);
        assertThat(oService.findAllOperations(savedBudget.getId()).size() == 1).isTrue();

        oService.removeOperation(savedBudget.getId(), op1.getId());
        assertThat(oService.findAllOperations(savedBudget.getId()).size() == 0).isTrue();
        assertThat(oService.removeOperation(0, 0)).isFalse();
        assertThat(oService.removeOperation(-1, -1)).isFalse();
    }

    @Test
    public void removeOperationDeleteItFromRepository() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());
        oService.addOperation(savedBudget.getId(), op1);
        assertThat(oService.findAllOperations(savedBudget.getId()).size() == 1).isTrue();

        assertThat(oRepository.findById(op1.getId()).orElse(null)).isEqualTo(op1);
        assertThat(oService.removeOperation(savedBudget.getId(), op1.getId())).isTrue();
        assertThat(oRepository.findById(op1.getId()).orElse(null)).isNull();
    }

    @Test
    public void removeOperationUpdateBudgetBalance() {
        Budget budget = ModelTestData.getBudget1();
        budget.getBalance().setCurrencies(Currencies.EUR);
        budget.getBalance().setAmount(BigDecimal.ZERO);
        budget = bService.saveBudget(budget);

        Price incomePrice = ModelTestData.getPrice1();
        incomePrice.setCurrencies(Currencies.EUR);
        incomePrice.setAmount(BigDecimal.valueOf(0.5));

        Price consumptionPrice = ModelTestData.getPrice1();
        consumptionPrice.setCurrencies(Currencies.EUR);
        consumptionPrice.setAmount(BigDecimal.valueOf(3));

        Element incomeEl = ModelTestData.getElement1();
        incomeEl.setType(ElementType.INCOME);
        incomeEl = eRepository.save(incomeEl);

        Element consumptionEl = ModelTestData.getElement1();
        consumptionEl.setType(ElementType.CONSUMPTION);
        consumptionEl = eRepository.save(consumptionEl);

        User user = uService.saveUser(ModelTestData.getUser1());
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());

        Operation incomeOp = oService.createOperation(user, incomeEl, provider, incomePrice);
        Operation consumptionOp = oService.createOperation(user, consumptionEl, provider, consumptionPrice);

        oService.addOperation(budget.getId(), incomeOp);
        oService.addOperation(budget.getId(), consumptionOp);
        budget = bService.findBudgetById(budget.getId());
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(-2.5));

        oService.removeOperation(budget.getId(), incomeOp.getId());
        budget = bService.findBudgetById(budget.getId());
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(-3.0));

        oService.removeOperation(budget.getId(), consumptionOp.getId());
        budget = bService.findBudgetById(budget.getId());
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(0.0));
    }

    @Test
    public void findAllOperations() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());

        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());

        oService.addOperation(savedBudget.getId(), op1);

        assertThat(oService.findAllOperations(savedBudget.getId()).size() == 1).isTrue();
        assertThat(oService.findAllOperations(0).size() == 0).isTrue();
        assertThat(oService.findAllOperations(-1).size() == 0).isTrue();
    }

    @Test
    public void findOperationsByElement() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element forSearch = eRepository.save(ModelTestData.getElement1());
        Element element = eRepository.save(ModelTestData.getElement1());
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = oService.createOperation(user, forSearch, provider, ModelTestData.getPrice1());
        Operation op2 = oService.createOperation(user, forSearch, provider, ModelTestData.getPrice1());
        Operation op3 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());

        oService.addOperation(budget.getId(), op1);
        oService.addOperation(budget.getId(), op2);
        oService.addOperation(budget.getId(), op3);

        assertThat(oService.findOperationsByElement(budget.getId(), forSearch.getId()).size() == 2).isTrue();
        assertThat(oService.findOperationsByElement(0, 0).size() == 0).isTrue();
        assertThat(oService.findOperationsByElement(-1, -1).size() == 0).isTrue();
    }

    @Test
    public void findOperationByElementProvider() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        Provider provider1 = epRepository.save(ModelTestData.getElementProvider1());
        Provider provider2 = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = oService.createOperation(user, element, provider1, ModelTestData.getPrice1());
        Operation op2 = oService.createOperation(user, element, provider1, ModelTestData.getPrice1());
        Operation op3 = oService.createOperation(user, element, provider2, ModelTestData.getPrice1());

        oService.addOperation(budget.getId(), op1);
        oService.addOperation(budget.getId(), op2);
        oService.addOperation(budget.getId(), op3);

        assertThat(oService.findOperationsByElementProvider(budget.getId(), provider1.getId()).size() == 2).isTrue();
        assertThat(oService.findOperationsByElementProvider(0, 0).size() == 0).isTrue();
        assertThat(oService.findOperationsByElementProvider(-1, -1).size() == 0).isTrue();

    }

    @Test
    public void findOperationsBetweenDates() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now().plusDays(1);

        Operation op1 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op1.setCreateOn(from.plusSeconds(2));
        Operation op2 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op2.setCreateOn(from.plusHours(2));
        Operation op3 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op3.setCreateOn(from.minusDays(12));

        oService.addOperation(budget.getId(), op1);
        oService.addOperation(budget.getId(), op2);
        oService.addOperation(budget.getId(), op3);

        assertThat(oService.findOperationsBetweenDates(budget.getId(), from, to).size() == 2).isTrue();
        assertThat(oService.findOperationsBetweenDates(budget.getId(), null, to).size() == 0).isTrue();
        assertThat(oService.findOperationsBetweenDates(budget.getId(), from, null).size() == 0).isTrue();
        assertThat(oService.findOperationsBetweenDates(0, from, to).size() == 0).isTrue();
        assertThat(oService.findOperationsBetweenDates(-1, from, to).size() == 0).isTrue();
    }

    @Test
    public void findOperationsBetweenPrices() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());
        Price from = ModelTestData.getPrice1();
        from.setAmount(BigDecimal.TEN);
        Price to = ModelTestData.getPrice1();
        to.setAmount(from.getAmount().add(from.getAmount()));

        Operation op1 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op1.getPrice().setAmount(from.getAmount().add(BigDecimal.valueOf(0.01)));
        Operation op2 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op2.getPrice().setAmount(to.getAmount().subtract(BigDecimal.valueOf(0.01)));
        Operation op3 = oService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op3.getPrice().setAmount(BigDecimal.ZERO);

        oService.addOperation(budget.getId(), op1);
        oService.addOperation(budget.getId(), op2);
        oService.addOperation(budget.getId(), op3);

        assertThat(oService.findOperationsBetweenPrices(budget.getId(), from, to).size() == 2).isTrue();
        assertThat(oService.findOperationsBetweenPrices(budget.getId(), null, to).size() == 0).isTrue();
        assertThat(oService.findOperationsBetweenPrices(budget.getId(), from, null).size() == 0).isTrue();
        assertThat(oService.findOperationsBetweenPrices(0, from, to).size() == 0).isTrue();
        assertThat(oService.findOperationsBetweenPrices(-1, from, to).size() == 0).isTrue();
    }

    @Test
    public void findOperationsByUser() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user1 = uService.saveUser(ModelTestData.getUser1());
        User user2 = uService.saveUser(ModelTestData.getUser2());
        Element element = eRepository.save(ModelTestData.getElement1());
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = oService.createOperation(user1, element, provider, ModelTestData.getPrice1());
        Operation op2 = oService.createOperation(user1, element, provider, ModelTestData.getPrice1());
        Operation op3 = oService.createOperation(user2, element, provider, ModelTestData.getPrice1());

        oService.addOperation(budget.getId(), op1);
        oService.addOperation(budget.getId(), op2);
        oService.addOperation(budget.getId(), op3);

        assertThat(oService.findOperationsByUser(budget.getId(), user1.getId()).size() == 2).isTrue();
        assertThat(oService.findOperationsByUser(0, 0).size() == 0).isTrue();
        assertThat(oService.findOperationsByUser(-1, 0 - 1).size() == 0).isTrue();
    }
}