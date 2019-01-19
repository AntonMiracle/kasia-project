package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.model.*;
import com.kasia.model.repository.BudgetElementProviderRepository;
import com.kasia.model.repository.BudgetElementRepository;
import com.kasia.model.repository.BudgetOperationRepository;
import com.kasia.model.repository.OperationRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetServiceIT {
    @Autowired
    private BudgetService bService;
    @Autowired
    private BudgetElementRepository beRepository;
    @Autowired
    private BudgetElementProviderRepository bepRepository;
    @Autowired
    private ElementService eService;
    @Autowired
    private ElementProviderService epService;
    @Autowired
    private UserService uService;
    @Autowired
    private OperationRepository oRepository;
    @Autowired
    private BudgetOperationRepository boRepository;

    @After
    public void cleanData() {
        beRepository.findAll().forEach(beRepository::delete);
        bepRepository.findAll().forEach(bepRepository::delete);
        boRepository.findAll().forEach(boRepository::delete);

        oRepository.findAll().forEach(oRepository::delete);
        bService.findAllBudgets().forEach(bService::deleteBudget);
        eService.findAll().forEach(eService::delete);
        epService.findAll().forEach(epService::delete);
        uService.findAllUsers().forEach(uService::deleteUser);
    }

    @Test
    public void saveNewBudget() {
        Budget expected = ModelTestData.getBudget1();

        bService.saveBudget(expected);

        assertThat(bService.findBudgetById(expected.getId())).isEqualTo(expected);
    }

    @Test
    public void saveUpdateBudget() {
        Budget expected = bService.saveBudget(ModelTestData.getBudget1());
        String newName = expected.getName() + expected.getName();
        expected.setName(newName);

        bService.saveBudget(expected);

        assertThat(bService.findBudgetById(expected.getId()).getName()).isEqualTo(newName);
    }

    @Test(expected = ValidationException.class)
    public void whenBudgetInvalidSaveNewBudgetThrowException() {
        Budget expected = ModelTestData.getBudget1();
        expected.setName("");

        bService.saveBudget(expected);
    }

    @Test
    public void findBudgetById() {
        Budget expected = ModelTestData.getBudget1();
        bService.saveBudget(expected);

        Budget actual = bService.findBudgetById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenIdNotExistFindBudgetByIdReturnNull() {
        Budget actual = bService.findBudgetById(123);

        assertThat(actual).isNull();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenIdZeroThenFindBudgetByIdThrowException() {
        bService.findBudgetById(0);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenIdNegativeThenFindBudgetByIdThrowException() {
        bService.findBudgetById(-1);
    }

    @Test
    public void findAllBudgets() {
        assertThat(bService.findAllBudgets().size() == 0).isTrue();

        bService.saveBudget(ModelTestData.getBudget1());
        bService.saveBudget(ModelTestData.getBudget1());

        assertThat(bService.findAllBudgets().size() == 2).isTrue();
    }

    @Test
    public void deleteBudget() {
        Budget expected = bService.saveBudget(ModelTestData.getBudget1());

        bService.deleteBudget(expected);

        assertThat(bService.findBudgetById(expected.getId())).isNull();
    }

    @Test(expected = ValidationException.class)
    public void deleteBudgetWithInvalidBudgetThrowException() {
        Budget expected = bService.saveBudget(ModelTestData.getBudget1());
        expected.setName("");

        bService.deleteBudget(expected);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void deleteBudgetWithInvalidIdThrowException() {
        Budget expected = bService.saveBudget(ModelTestData.getBudget1());
        expected.setId(0);

        bService.deleteBudget(expected);
    }

    @Test
    public void createBudget() {
        Budget expected = ModelTestData.getBudget1();
        Budget actual = bService.createBudget(expected.getName(), expected.getBalance());

        assertThat(actual.getId() == 0).isTrue();
        assertThat(actual.getCreateOn().compareTo(LocalDateTime.now().plusSeconds(2)) < 0).isTrue();
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getBalance()).isEqualTo((expected.getBalance()));
    }

    @Test(expected = ValidationException.class)
    public void whenCreateInvalidBudgetThenThrowException() {
        bService.createBudget("", null);
    }

    private BudgetElement getSavedForTestCleanBudgetElement() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        BudgetElement be = ModelTestData.getBudgetElement1();
        be.getElements().clear();
        be.setBudget(savedBudget);
        return beRepository.save(be);
    }

    @Test
    public void elementUnique() {
        Element savedElement = eService.save(ModelTestData.getElement1());
        BudgetElement be = getSavedForTestCleanBudgetElement();
        be.getElements().add(savedElement);
        beRepository.save(be);

        assertThat(bService.isElementUnique(be.getBudget(), savedElement)).isFalse();
        assertThat(bService.isElementUnique(be.getBudget(), ModelTestData.getElement2())).isTrue();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidIsElementUniqueThrowException() {
        Budget b = ModelTestData.getBudget1();

        bService.isElementUnique(b, ModelTestData.getElement1());
    }

    @Test(expected = ValidationException.class)
    public void whenElementInvalidIsElementUniqueThrowException() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        Element element = ModelTestData.getElement1();
        element.setName("");

        bService.isElementUnique(budget, element);
    }

    @Test
    public void addElement() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        int elementsBeforeAdd = beRepository.findByBudgetId(be.getBudget().getId()).get().getElements().size();
        Element element = ModelTestData.getElement1();

        assertThat(bService.addElement(be.getBudget(), element)).isTrue();

        int elementsAfterAdd = beRepository.findByBudgetId(be.getBudget().getId()).get().getElements().size();
        assertThat(elementsAfterAdd == (elementsBeforeAdd + 1)).isTrue();
    }

    @Test
    public void addOnlyUniqueElement() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        int elementsBeforeAdd = beRepository.findByBudgetId(be.getBudget().getId()).get().getElements().size();
        Element element = ModelTestData.getElement1();
        Element element2 = ModelTestData.getElement2();
        element2.setName(element.getName());

        assertThat(bService.addElement(be.getBudget(), element)).isTrue();
        assertThat(bService.addElement(be.getBudget(), element2)).isFalse();

        int elementsAfterAdd = beRepository.findByBudgetId(be.getBudget().getId()).get().getElements().size();
        assertThat(elementsAfterAdd == (elementsBeforeAdd + 1)).isTrue();
    }

    @Test(expected = ValidationException.class)
    public void whenBudgetInvalidAddElementThrowException() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element = ModelTestData.getElement1();
        be.getBudget().setName("");

        bService.addElement(be.getBudget(), element);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetWithInvalidIdAddElementThrowException() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element = ModelTestData.getElement1();
        be.getBudget().setId(0);

        bService.addElement(be.getBudget(), element);
    }

    @Test(expected = ValidationException.class)
    public void whenElementInvalidAddElementThrowException() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element = ModelTestData.getElement1();
        element.setName("");

        bService.addElement(be.getBudget(), element);
    }

    @Test
    public void removeElement() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element = ModelTestData.getElement1();
        bService.addElement(be.getBudget(), element);
        int elementsBeforeAdd = beRepository.findByBudgetId(be.getBudget().getId()).get().getElements().size();

        assertThat(bService.removeElement(be.getBudget(), element)).isTrue();

        int elementsAfterAdd = beRepository.findByBudgetId(be.getBudget().getId()).get().getElements().size();
        assertThat(elementsAfterAdd == (elementsBeforeAdd - 1)).isTrue();
    }

    @Test(expected = ValidationException.class)
    public void whenElementInvalidRemoveElementThrowException() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element = ModelTestData.getElement1();
        bService.addElement(be.getBudget(), element);
        element.setName("");

        bService.removeElement(be.getBudget(), element);
    }

    @Test(expected = ValidationException.class)
    public void whenBudgetInvalidRemoveElementThrowException() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element = ModelTestData.getElement1();
        bService.addElement(be.getBudget(), element);
        be.getBudget().setName("");

        bService.removeElement(be.getBudget(), element);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidRemoveElementThrowException() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element = ModelTestData.getElement1();
        bService.addElement(be.getBudget(), element);
        be.getBudget().setId(0);

        bService.removeElement(be.getBudget(), element);
    }

    @Test
    public void findElementByName() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element expected = ModelTestData.getElement1();
        bService.addElement(be.getBudget(), expected);

        Element actual = bService.findElementByName(be.getBudget(), expected.getName());

        assertThat(actual).isEqualTo(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenElementNotExistFindElementByNameReturnNull() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element expected = ModelTestData.getElement1();
        bService.addElement(be.getBudget(), expected);

        Element actual = bService.findElementByName(be.getBudget(), expected.getName() + expected.getName());

        assertThat(actual).isNull();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidFindElementByNameThrowException() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element expected = ModelTestData.getElement1();
        bService.addElement(be.getBudget(), expected);
        be.getBudget().setId(0);

        bService.findElementByName(be.getBudget(), expected.getName());
    }

    @Test
    public void findAllElements() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element1 = ModelTestData.getElement1();
        Element element2 = ModelTestData.getElement2();
        bService.addElement(be.getBudget(), element1);
        bService.addElement(be.getBudget(), element2);

        assertThat(bService.findAllElements(be.getBudget()).size() == 2).isTrue();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidFindAllElementsThrowException() {
        BudgetElement be = getSavedForTestCleanBudgetElement();

        be.getBudget().setId(0);

        bService.findAllElements(be.getBudget());
    }

    private BudgetElementProvider getSavedForTestCleanBudgetElementProvider() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        BudgetElementProvider be = ModelTestData.getBudgetElementProvider1();
        be.getElementProviders().clear();
        be.setBudget(savedBudget);
        return bepRepository.save(be);
    }

    @Test
    public void elementProviderUnique() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider savedElementProvider = epService.save(ModelTestData.getElementProvider1());
        bep.getElementProviders().add(savedElementProvider);
        bepRepository.save(bep);

        assertThat(bService.isElementProviderUnique(bep.getBudget(), savedElementProvider)).isFalse();
        assertThat(bService.isElementProviderUnique(bep.getBudget(), ModelTestData.getElementProvider2())).isTrue();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidIsElementProviderUniqueThrowException() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider savedElementProvider = epService.save(ModelTestData.getElementProvider1());
        bep.getElementProviders().add(savedElementProvider);

        bep.getBudget().setId(0);

        bService.isElementProviderUnique(bep.getBudget(), savedElementProvider);
    }

    @Test(expected = ValidationException.class)
    public void whenElementProviderInvalidIsElementProviderUniqueThrowException() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider savedElementProvider = epService.save(ModelTestData.getElementProvider1());
        bep.getElementProviders().add(savedElementProvider);

        savedElementProvider.setName("");

        bService.isElementProviderUnique(bep.getBudget(), savedElementProvider);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidAddElementProviderThrowException() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();

        bep.getBudget().setId(0);

        bService.addElementProvider(bep.getBudget(), provider);
    }

    @Test(expected = ValidationException.class)
    public void whenBudgetInvalidAddElementProviderThrowException() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();

        bep.getBudget().setName("");

        bService.addElementProvider(bep.getBudget(), provider);
    }

    @Test(expected = ValidationException.class)
    public void whenProviderInvalidAddElementProviderThrowException() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();

        provider.setName("");

        bService.addElementProvider(bep.getBudget(), provider);
    }

    @Test
    public void addElementProvider() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();
        int providersBeforeAdd = bepRepository.findByBudgetId(bep.getBudget().getId()).get().getElementProviders().size();

        assertThat(bService.addElementProvider(bep.getBudget(), provider)).isTrue();

        int providersAfterAdd = bepRepository.findByBudgetId(bep.getBudget().getId()).get().getElementProviders().size();
        assertThat(providersAfterAdd == (providersBeforeAdd + 1)).isTrue();
    }

    @Test
    public void removeElementProvider() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();
        bService.addElementProvider(bep.getBudget(), provider);
        int providersBeforeRemove = bepRepository.findByBudgetId(bep.getBudget().getId()).get().getElementProviders().size();

        assertThat(bService.removeElementProvider(bep.getBudget(), provider)).isTrue();

        int providersAfterRemove = bepRepository.findByBudgetId(bep.getBudget().getId()).get().getElementProviders().size();
        assertThat(providersAfterRemove == (providersBeforeRemove - 1)).isTrue();
    }

    @Test(expected = ValidationException.class)
    public void whenBudgetInvalidRemoveElementProviderThrowException() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();
        bService.addElementProvider(bep.getBudget(), provider);

        bep.getBudget().setName("");

        bService.removeElementProvider(bep.getBudget(), provider);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidRemoveElementProviderThrowException() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();
        bService.addElementProvider(bep.getBudget(), provider);

        bep.getBudget().setId(0);

        bService.removeElementProvider(bep.getBudget(), provider);
    }

    @Test(expected = ValidationException.class)
    public void whenElementProviderInvalidRemoveElementProviderThrowException() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();
        bService.addElementProvider(bep.getBudget(), provider);

        provider.setName("");

        bService.removeElementProvider(bep.getBudget(), provider);
    }

    @Test
    public void findElementProviderByName() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider expected = ModelTestData.getElementProvider1();
        bService.addElementProvider(bep.getBudget(), expected);

        ElementProvider actual = bService.findElementProviderByName(bep.getBudget(), expected.getName());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenNameNotExistFindElementProviderByNameReturnNull() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();

        ElementProvider actual = bService.findElementProviderByName(bep.getBudget(), ModelTestData.getElementProvider1().getName());

        assertThat(actual).isNull();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidFindElementProviderByNameThrowException() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();

        bep.getBudget().setId(0);

        bService.findElementProviderByName(bep.getBudget(), ModelTestData.getElementProvider1().getName());
    }

    @Test
    public void findAllElementProviders() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider1 = ModelTestData.getElementProvider1();
        ElementProvider provider2 = ModelTestData.getElementProvider2();
        bService.addElementProvider(bep.getBudget(), provider1);
        bService.addElementProvider(bep.getBudget(), provider2);

        assertThat(bService.findAllElementProviders(bep.getBudget()).size() == 2).isTrue();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidFindAllElementProvidersThrowException() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();

        bep.getBudget().setId(0);

        bService.findAllElementProviders(bep.getBudget()).size();
    }

    private Operation getValidOperationWithNestedPositiveId() {
        Operation operation = ModelTestData.getOperation1();
        operation.getElementProvider().setId(1);
        operation.getElement().setId(1);
        operation.getUser().setId(1);
        return operation;
    }

    @Test
    public void createOperation() {
        Operation op = getValidOperationWithNestedPositiveId();
        Operation actual = bService.createOperation(op.getUser(), op.getElement(), op.getElementProvider(), op.getPrice());

        assertThat(actual.getUser()).isEqualTo(op.getUser());
        assertThat(actual.getPrice()).isEqualTo(op.getPrice());
        assertThat(actual.getElement()).isEqualTo(op.getElement());
        assertThat(actual.getElementProvider()).isEqualTo(op.getElementProvider());
        assertThat(actual.getCreateOn().compareTo(LocalDateTime.now().plusSeconds(3)) < 0).isTrue();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenUserIdInvalidCreateOperationThrowException() {
        Operation op = getValidOperationWithNestedPositiveId();
        op.getUser().setId(0);

        bService.createOperation(op.getUser(), op.getElement(), op.getElementProvider(), op.getPrice());
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenElementIdInvalidCreateOperationThrowException() {
        Operation op = getValidOperationWithNestedPositiveId();
        op.getElement().setId(0);

        bService.createOperation(op.getUser(), op.getElement(), op.getElementProvider(), op.getPrice());
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenElementProviderIdInvalidCreateOperationThrowException() {
        Operation op = getValidOperationWithNestedPositiveId();
        op.getElementProvider().setId(0);

        bService.createOperation(op.getUser(), op.getElement(), op.getElementProvider(), op.getPrice());
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidCreateOperationThrowException() {
        Operation op = getValidOperationWithNestedPositiveId();
        op.getUser().setName("");

        bService.createOperation(op.getUser(), op.getElement(), op.getElementProvider(), op.getPrice());
    }

    @Test(expected = ValidationException.class)
    public void whenElementInvalidCreateOperationThrowException() {
        Operation op = getValidOperationWithNestedPositiveId();
        op.getElement().setName("");

        bService.createOperation(op.getUser(), op.getElement(), op.getElementProvider(), op.getPrice());
    }

    @Test(expected = ValidationException.class)
    public void whenElementProviderInvalidCreateOperationThrowException() {
        Operation op = getValidOperationWithNestedPositiveId();
        op.getElementProvider().setName("");

        bService.createOperation(op.getUser(), op.getElement(), op.getElementProvider(), op.getPrice());
    }

    @Test
    public void findAllOperation() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());

        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eService.save(ModelTestData.getElement1());
        ElementProvider provider = epService.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());

        bService.addOperation(savedBudget, op1);

        assertThat(bService.findAllOperations(savedBudget).size() == 1).isTrue();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidFindAllOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());

        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eService.save(ModelTestData.getElement1());
        ElementProvider provider = epService.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());

        bService.addOperation(savedBudget, op1);
        savedBudget.setId(0);

        bService.findAllOperations(savedBudget);
    }

    @Test
    public void addOperation() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eService.save(ModelTestData.getElement1());
        ElementProvider provider = epService.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        Operation op2 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());

        assertThat(bService.findAllOperations(savedBudget).size() == 0).isTrue();

        bService.addOperation(savedBudget, op1);
        assertThat(bService.findAllOperations(savedBudget).size() == 1).isTrue();

        bService.addOperation(savedBudget, op2);
        assertThat(bService.findAllOperations(savedBudget).size() == 2).isTrue();
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
        incomeEl = eService.save(incomeEl);

        Element consumptionEl = ModelTestData.getElement1();
        consumptionEl.setType(ElementType.CONSUMPTION);
        consumptionEl = eService.save(consumptionEl);

        User user = uService.saveUser(ModelTestData.getUser1());
        ElementProvider provider = epService.save(ModelTestData.getElementProvider1());

        Operation incomeOp = bService.createOperation(user, incomeEl, provider, incomePrice);
        Operation consumptionOp = bService.createOperation(user, consumptionEl, provider, consumptionPrice);

        bService.addOperation(budget, incomeOp);
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(0.5));

        bService.addOperation(budget, consumptionOp);
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(-2.5));
    }

    @Test(expected = CurrenciesNotEqualsRuntimeException.class)
    public void whenCurrenciesNotEqualsAddOperationThrowException() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eService.save(ModelTestData.getElement1());
        ElementProvider provider = epService.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op1.getPrice().setCurrencies(Currencies.EUR);
        budget.getBalance().setCurrencies(Currencies.PLN);

        bService.addOperation(budget, op1);
    }

    private Operation getSavedOperationForCheckRuntimeException() {
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eService.save(ModelTestData.getElement1());
        ElementProvider provider = epService.save(ModelTestData.getElementProvider1());
        return bService.createOperation(user, element, provider, ModelTestData.getPrice1());
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getUser().setName("");

        bService.addOperation(savedBudget, op);
    }

    @Test(expected = ValidationException.class)
    public void whenElementInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getElement().setName("");

        bService.addOperation(savedBudget, op);
    }

    @Test(expected = ValidationException.class)
    public void whenElementProviderInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getElementProvider().setName("");

        bService.addOperation(savedBudget, op);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenUserIdInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getUser().setId(0);

        bService.addOperation(savedBudget, op);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenElementIdInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getElement().setId(0);

        bService.addOperation(savedBudget, op);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenElementProviderIdInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getElementProvider().setId(0);

        bService.addOperation(savedBudget, op);
    }

    @Test
    public void removeOperation() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eService.save(ModelTestData.getElement1());
        ElementProvider provider = epService.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        bService.addOperation(savedBudget, op1);
        assertThat(bService.findAllOperations(savedBudget).size() == 1).isTrue();

        bService.removeOperation(savedBudget, op1);
        assertThat(bService.findAllOperations(savedBudget).size() == 0).isTrue();
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
        incomeEl = eService.save(incomeEl);

        Element consumptionEl = ModelTestData.getElement1();
        consumptionEl.setType(ElementType.CONSUMPTION);
        consumptionEl = eService.save(consumptionEl);

        User user = uService.saveUser(ModelTestData.getUser1());
        ElementProvider provider = epService.save(ModelTestData.getElementProvider1());

        Operation incomeOp = bService.createOperation(user, incomeEl, provider, incomePrice);
        Operation consumptionOp = bService.createOperation(user, consumptionEl, provider, consumptionPrice);

        bService.addOperation(budget, incomeOp);
        bService.addOperation(budget, consumptionOp);
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(-2.5));

        bService.removeOperation(budget, incomeOp);
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(-3.0));

        bService.removeOperation(budget, consumptionOp);
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(0.0));

    }

    @Test(expected = CurrenciesNotEqualsRuntimeException.class)
    public void whenCurrenciesNotEqualsRemoveOperationThrowException() {
        Budget budget = ModelTestData.getBudget1();
        budget.getBalance().setCurrencies(Currencies.PLN);
        Budget savedBudget = bService.saveBudget(budget);
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eService.save(ModelTestData.getElement1());
        ElementProvider provider = epService.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        bService.addOperation(savedBudget, op1);
        op1.getPrice().setCurrencies(Currencies.EUR);

        bService.removeOperation(savedBudget, op1);
    }

    private boolean isWasIdInvalidRuntimeExceptionWhenRemoveOperation(Budget budget, Operation operation) {
        try {
            bService.removeOperation(budget, operation);
        } catch (IdInvalidRuntimeException ex) {
            return true;
        }
        return false;
    }

    @Test
    public void whenIdInvalidRemoveOperationThrowException() {
        Budget bu = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();
        bService.addOperation(bu, op);
        int countException = 0;

        long idTemp = bu.getId();
        bu.setId(0);
        if (isWasIdInvalidRuntimeExceptionWhenRemoveOperation(bu, op)) countException++;
        bu.setId(idTemp);

        idTemp = op.getUser().getId();
        op.getUser().setId(0);
        if (isWasIdInvalidRuntimeExceptionWhenRemoveOperation(bu, op)) countException++;
        op.getUser().setId(idTemp);

        idTemp = op.getElement().getId();
        op.getElement().setId(0);
        if (isWasIdInvalidRuntimeExceptionWhenRemoveOperation(bu, op)) countException++;
        op.getElement().setId(idTemp);

        idTemp = op.getElementProvider().getId();
        op.getElementProvider().setId(0);
        if (isWasIdInvalidRuntimeExceptionWhenRemoveOperation(bu, op)) countException++;
        op.getElementProvider().setId(idTemp);

        idTemp = op.getId();
        op.setId(0);
        if (isWasIdInvalidRuntimeExceptionWhenRemoveOperation(bu, op)) countException++;
        op.setId(idTemp);

        assertThat(countException == 5).isTrue();
    }

    private boolean isWasValidationExceptionWhenRemoveOperation(Budget budget, Operation operation) {
        try {
            bService.removeOperation(budget, operation);
        } catch (ValidationException ex) {
            return true;
        }
        return false;
    }

    @Test
    public void whenInvalidRemoveOperationThrowException() {
        Budget bu = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();
        bService.addOperation(bu, op);
        int countException = 0;

        String temp = bu.getName();
        bu.setName("");
        if (isWasValidationExceptionWhenRemoveOperation(bu, op)) countException++;
        bu.setName(temp);

        temp = op.getUser().getName();
        op.getUser().setName("");
        if (isWasValidationExceptionWhenRemoveOperation(bu, op)) countException++;
        op.getUser().setName(temp);

        temp = op.getElement().getName();
        op.getElement().setName("");
        if (isWasValidationExceptionWhenRemoveOperation(bu, op)) countException++;
        op.getElement().setName(temp);

        temp = op.getElementProvider().getName();
        op.getElementProvider().setName("");
        if (isWasValidationExceptionWhenRemoveOperation(bu, op)) countException++;
        op.getElementProvider().setName(temp);

        assertThat(countException == 4).isTrue();
    }

    @Test
    public void findOperationByElement() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element forSearch = eService.save(ModelTestData.getElement1());
        Element element = eService.save(ModelTestData.getElement1());
        ElementProvider provider = epService.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, forSearch, provider, ModelTestData.getPrice1());
        Operation op2 = bService.createOperation(user, forSearch, provider, ModelTestData.getPrice1());
        Operation op3 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());

        bService.addOperation(budget, op1);
        bService.addOperation(budget, op2);
        bService.addOperation(budget, op3);

        assertThat(bService.findOperationsByElement(budget, forSearch).size() == 2).isTrue();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidFindOperationByElementThrowException() {
        Budget budget = ModelTestData.getBudget1();
        Element element = ModelTestData.getElement1();
        element.setId(22);
        budget.setId(0);

        bService.findOperationsByElement(budget, element);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenElementIdInvalidFindOperationByElementThrowException() {
        Budget budget = ModelTestData.getBudget1();
        Element element = ModelTestData.getElement1();
        element.setId(0);
        budget.setId(10);

        bService.findOperationsByElement(budget, element);
    }

    @Test(expected = ValidationException.class)
    public void whenElementInvalidFindOperationByElementThrowException() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        Element element = eService.save(ModelTestData.getElement1());
        element.setName("");

        bService.findOperationsByElement(budget, element);
    }

    @Test
    public void findOperationByElementProvider() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eService.save(ModelTestData.getElement1());
        ElementProvider provider1 = epService.save(ModelTestData.getElementProvider1());
        ElementProvider provider2 = epService.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider1, ModelTestData.getPrice1());
        Operation op2 = bService.createOperation(user, element, provider1, ModelTestData.getPrice1());
        Operation op3 = bService.createOperation(user, element, provider2, ModelTestData.getPrice1());

        bService.addOperation(budget, op1);
        bService.addOperation(budget, op2);
        bService.addOperation(budget, op3);

        assertThat(bService.findOperationsByElementProvider(budget, provider1).size() == 2).isTrue();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidFindOperationByElementProviderThrowException() {
        Budget bu = ModelTestData.getBudget1();
        ElementProvider ep = ModelTestData.getElementProvider1();
        ep.setId(1);

        bService.findOperationsByElementProvider(bu, ep);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenElementProviderIdInvalidFindOperationByElementProviderThrowException() {
        Budget bu = ModelTestData.getBudget1();
        ElementProvider ep = ModelTestData.getElementProvider1();
        bu.setId(1);

        bService.findOperationsByElementProvider(bu, ep);
    }

    @Test(expected = ValidationException.class)
    public void whenElementProviderInvalidFindOperationByElementProviderThrowException() {
        Budget bu = ModelTestData.getBudget1();
        ElementProvider ep = ModelTestData.getElementProvider1();
        bu.setId(1);
        ep.setId(1);
        ep.setName("");

        bService.findOperationsByElementProvider(bu, ep);
    }

    @Test
    public void findOperationsByUser() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user1 = uService.saveUser(ModelTestData.getUser1());
        User user2 = uService.saveUser(ModelTestData.getUser2());
        Element element = eService.save(ModelTestData.getElement1());
        ElementProvider provider = epService.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user1, element, provider, ModelTestData.getPrice1());
        Operation op2 = bService.createOperation(user1, element, provider, ModelTestData.getPrice1());
        Operation op3 = bService.createOperation(user2, element, provider, ModelTestData.getPrice1());

        bService.addOperation(budget, op1);
        bService.addOperation(budget, op2);
        bService.addOperation(budget, op3);

        assertThat(bService.findOperationsByUser(budget, user1).size() == 2).isTrue();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenUserIdInvalidFindOperationsByUserThrowException() {
        Budget budget = ModelTestData.getBudget1();
        User user = ModelTestData.getUser1();
        budget.setId(1);

        bService.findOperationsByUser(budget, user);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenBudgetIdInvalidFindOperationsByUserThrowException() {
        Budget budget = ModelTestData.getBudget1();
        User user = ModelTestData.getUser1();
        user.setId(1);

        bService.findOperationsByUser(budget, user);
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidFindOperationsByUserThrowException() {
        Budget budget = ModelTestData.getBudget1();
        User user = ModelTestData.getUser1();
        user.setId(1);
        user.setName("");
        budget.setId(1);

        bService.findOperationsByUser(budget, user);
    }
}