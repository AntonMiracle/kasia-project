package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.CurrenciesNotEqualsRuntimeException;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.exception.IntervalRuntimeException;
import com.kasia.model.*;
import com.kasia.model.repository.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private UserConnectBudgetRepository ucbRepository;

    @After
    public void cleanData() {
        beRepository.findAll().forEach(beRepository::delete);
        bepRepository.findAll().forEach(bepRepository::delete);
        boRepository.findAll().forEach(boRepository::delete);
        ucbRepository.findAll().forEach(ucbRepository::delete);

        oRepository.findAll().forEach(oRepository::delete);
        bService.findAllBudgets().forEach(budget -> bService.deleteBudget(budget.getId()));
        eRepository.findAll().forEach(eRepository::delete);
        epRepository.findAll().forEach(epRepository::delete);
        uService.findAllUsers().forEach(user -> uService.deleteUser(user.getId()));
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
        assertThat(bService.findBudgetById(0)).isNull();
        assertThat(bService.findBudgetById(-1)).isNull();
    }

    @Test
    public void whenIdNotExistFindBudgetByIdReturnNull() {
        Budget actual = bService.findBudgetById(123);

        assertThat(actual).isNull();
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

        assertThat(bService.deleteBudget(expected.getId())).isTrue();
        assertThat(bService.findBudgetById(expected.getId())).isNull();
        assertThat(bService.deleteBudget(0)).isFalse();
        assertThat(bService.deleteBudget(-1)).isFalse();
    }

    @Test
    public void deleteBudgetDeleteAllConnectionWithUsers() {
        User connect1 = uService.saveUser(ModelTestData.getUser1());
        User connect2 = uService.saveUser(ModelTestData.getUser2());

        Budget budget = bService.saveBudget(ModelTestData.getBudget1());

        UserConnectBudget ucb1 = new UserConnectBudget(connect1, new HashSet<>());
        UserConnectBudget ucb2 = new UserConnectBudget(connect2, new HashSet<>());
        ucb1.getConnectBudgets().add(budget);
        ucb2.getConnectBudgets().add(budget);
        ucbRepository.save(ucb1);
        ucbRepository.save(ucb2);

        assertThat(ucbRepository.findByUserId(connect1.getId()).get().getConnectBudgets().size() == 1).isTrue();
        assertThat(ucbRepository.findByUserId(connect2.getId()).get().getConnectBudgets().size() == 1).isTrue();

        bService.deleteBudget(budget.getId());

        assertThat(ucbRepository.findByUserId(connect1.getId()).get().getConnectBudgets().size() == 0).isTrue();
        assertThat(ucbRepository.findByUserId(connect2.getId()).get().getConnectBudgets().size() == 0).isTrue();
    }

    @Test
    public void whenDeleteBudgetDeleteAllElementsOperationsElementProvidersInBudget() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation operation = bService.createOperation(user, element, provider, ModelTestData.getPrice1());

        bService.addElement(budget.getId(), element);
        bService.addElementProvider(budget.getId(), provider);
        bService.addOperation(budget.getId(), operation);

        assertThat(bService.findBudgetById(budget.getId())).isEqualTo(budget);
        assertThat(eRepository.findById(element.getId()).get()).isEqualTo(element);
        assertThat(epRepository.findById(provider.getId()).get()).isEqualTo(provider);
        assertThat(oRepository.findById(operation.getId()).get()).isEqualTo(operation);

        bService.deleteBudget(budget.getId());

        assertThat(bService.findBudgetById(budget.getId())).isNull();
        assertThat(eRepository.findById(element.getId()).isPresent()).isFalse();
        assertThat(epRepository.findById(provider.getId()).isPresent()).isFalse();
        assertThat(oRepository.findById(operation.getId()).isPresent()).isFalse();
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
        Element savedElement = eRepository.save(ModelTestData.getElement1());
        BudgetElement be = getSavedForTestCleanBudgetElement();
        be.getElements().add(savedElement);
        beRepository.save(be);

        assertThat(bService.isElementUnique(be.getBudget().getId(), savedElement.getName())).isFalse();
        assertThat(bService.isElementUnique(be.getBudget().getId(), ModelTestData.getElement2().getName())).isTrue();
        assertThat(bService.isElementUnique(be.getBudget().getId(), ModelTestData.getElement2().getName())).isTrue();
        assertThat(bService.isElementUnique(0, null)).isFalse();
        assertThat(bService.isElementUnique(-1, null)).isFalse();
    }

    private int countElementsInBudget(Budget budget) {
        return beRepository.findByBudgetId(budget.getId())
                .map(budgetElement -> budgetElement.getElements().size())
                .orElse(0);
    }

    @Test
    public void addElement() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        int elementsBeforeAdd = countElementsInBudget(be.getBudget());
        Element element1 = ModelTestData.getElement1();
        Element element2 = ModelTestData.getElement1();
        Element element3 = ModelTestData.getElement1();
        element2.setName(element1.getName() + "new");

        assertThat(element1.getName().equals(element3.getName())).isTrue();
        assertThat(bService.addElement(be.getBudget().getId(), element1)).isTrue();
        assertThat(bService.addElement(be.getBudget().getId(), element2)).isTrue();
        assertThat(bService.addElement(be.getBudget().getId(), element3)).isFalse();

        int elementsAfterAdd = countElementsInBudget(be.getBudget());
        assertThat(elementsAfterAdd == (elementsBeforeAdd + 2)).isTrue();
    }

    @Test
    public void addElementAddElementInElementRepository() {
        BudgetElement be = getSavedForTestCleanBudgetElement();

        Set<Element> elementsBefore = new HashSet<>();
        eRepository.findAll().forEach(elementsBefore::add);

        bService.addElement(be.getBudget().getId(), ModelTestData.getElement1());

        Set<Element> elementsAfter = new HashSet<>();
        eRepository.findAll().forEach(elementsAfter::add);

        assertThat(elementsAfter.size() == (elementsBefore.size() + 1)).isTrue();
    }

    @Test
    public void addOnlyUniqueElement() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        int elementsBeforeAdd = countElementsInBudget(be.getBudget());
        Element element = ModelTestData.getElement1();
        Element element2 = ModelTestData.getElement2();
        element2.setName(element.getName());

        assertThat(bService.addElement(be.getBudget().getId(), element)).isTrue();
        assertThat(bService.addElement(be.getBudget().getId(), element2)).isFalse();

        int elementsAfterAdd = countElementsInBudget(be.getBudget());
        assertThat(elementsAfterAdd == (elementsBeforeAdd + 1)).isTrue();
    }


    @Test(expected = ValidationException.class)
    public void whenElementInvalidAddElementThrowException() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element = ModelTestData.getElement1();
        element.setName("");

        bService.addElement(be.getBudget().getId(), element);
    }

    @Test
    public void removeElement() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element = ModelTestData.getElement1();
        bService.addElement(be.getBudget().getId(), element);
        int elementsBeforeAdd = countElementsInBudget(be.getBudget());

        assertThat(bService.removeElement(be.getBudget().getId(), element.getId())).isTrue();

        int elementsAfterAdd = countElementsInBudget(be.getBudget());
        assertThat(elementsAfterAdd == (elementsBeforeAdd - 1)).isTrue();
    }

    @Test
    public void removeElementRemoveElementFromElementRepository() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element = ModelTestData.getElement1();
        bService.addElement(be.getBudget().getId(), element);

        Set<Element> elementsBefore = new HashSet<>();
        eRepository.findAll().forEach(elementsBefore::add);

        bService.removeElement(be.getBudget().getId(), element.getId());

        Set<Element> elementsAfter = new HashSet<>();
        eRepository.findAll().forEach(elementsAfter::add);

        assertThat(elementsAfter.size() == (elementsBefore.size() - 1)).isTrue();
    }


    @Test
    public void findElementByName() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element expected = ModelTestData.getElement1();
        bService.addElement(be.getBudget().getId(), expected);

        Element actual = bService.findElementByName(be.getBudget().getId(), expected.getName());

        assertThat(actual).isEqualTo(expected);
        assertThat(actual).isEqualTo(expected);
        assertThat(bService.findElementByName(0, "name")).isNull();
        assertThat(bService.findElementByName(-1, "name")).isNull();
        assertThat(bService.findElementByName(0, null)).isNull();
    }

    @Test
    public void whenElementNotExistFindElementByNameReturnNull() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element expected = ModelTestData.getElement1();
        bService.addElement(be.getBudget().getId(), expected);

        Element actual = bService.findElementByName(be.getBudget().getId(), expected.getName() + expected.getName());

        assertThat(actual).isNull();
    }

    @Test
    public void findAllElements() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element1 = ModelTestData.getElement1();
        Element element2 = ModelTestData.getElement2();
        bService.addElement(be.getBudget().getId(), element1);
        bService.addElement(be.getBudget().getId(), element2);

        assertThat(bService.findAllElements(be.getBudget().getId()).size() == 2).isTrue();
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
        ElementProvider savedElementProvider = epRepository.save(ModelTestData.getElementProvider1());
        bep.getElementProviders().add(savedElementProvider);
        bepRepository.save(bep);

        assertThat(bService.isElementProviderUnique(bep.getBudget().getId(), savedElementProvider.getName())).isFalse();
        assertThat(bService.isElementProviderUnique(bep.getBudget().getId(), ModelTestData.getElementProvider2().getName())).isTrue();
    }


    @Test(expected = ValidationException.class)
    public void whenProviderInvalidAddElementProviderThrowException() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();

        provider.setName("");

        bService.addElementProvider(bep.getBudget().getId(), provider);
    }

    private int countElementProvidersInBudget(Budget budget) {
        return bepRepository.findByBudgetId(budget.getId())
                .map(bep -> bep.getElementProviders().size())
                .orElse(0);
    }

    @Test
    public void addElementProvider() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider1 = ModelTestData.getElementProvider1();
        ElementProvider provider2 = ModelTestData.getElementProvider1();
        provider2.setName(provider1.getName() + "new");
        ElementProvider provider3 = ModelTestData.getElementProvider1();
        int providersBeforeAdd = countElementProvidersInBudget(bep.getBudget());

        assertThat(provider1.getName()).isEqualTo(provider3.getName());
        assertThat(bService.addElementProvider(bep.getBudget().getId(), provider1)).isTrue();
        assertThat(bService.addElementProvider(bep.getBudget().getId(), provider2)).isTrue();
        assertThat(bService.addElementProvider(bep.getBudget().getId(), provider3)).isFalse();

        int providersAfterAdd = countElementProvidersInBudget(bep.getBudget());
        assertThat(providersAfterAdd == (providersBeforeAdd + 2)).isTrue();
    }

    @Test
    public void addElementProviderAddItToElementProviderRepository() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();

        Set<ElementProvider> elementsBefore = new HashSet<>();
        epRepository.findAll().forEach(elementsBefore::add);

        bService.addElementProvider(bep.getBudget().getId(), provider);

        Set<ElementProvider> elementsAfter = new HashSet<>();
        epRepository.findAll().forEach(elementsAfter::add);

        assertThat(elementsAfter.size() == (elementsBefore.size() + 1)).isTrue();
    }

    @Test
    public void removeElementProvider() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();
        bService.addElementProvider(bep.getBudget().getId(), provider);
        int providersBeforeRemove = countElementProvidersInBudget(bep.getBudget());

        assertThat(bService.removeElementProvider(bep.getBudget().getId(), provider.getId())).isTrue();

        int providersAfterRemove = countElementProvidersInBudget(bep.getBudget());
        assertThat(providersAfterRemove == (providersBeforeRemove - 1)).isTrue();
    }

    @Test
    public void removeElementProviderRemoveItFromElementProviderRepository() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider = ModelTestData.getElementProvider1();
        bService.addElementProvider(bep.getBudget().getId(), provider);

        Set<ElementProvider> elementsBefore = new HashSet<>();
        epRepository.findAll().forEach(elementsBefore::add);

        bService.removeElementProvider(bep.getBudget().getId(), provider.getId());

        Set<ElementProvider> elementsAfter = new HashSet<>();
        epRepository.findAll().forEach(elementsAfter::add);

        assertThat(elementsAfter.size() == (elementsBefore.size() - 1)).isTrue();
    }

    @Test
    public void findElementProviderByName() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider expected = ModelTestData.getElementProvider1();
        bService.addElementProvider(bep.getBudget().getId(), expected);

        ElementProvider actual = bService.findElementProviderByName(bep.getBudget().getId(), expected.getName());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenNameNotExistFindElementProviderByNameReturnNull() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();

        ElementProvider actual = bService.findElementProviderByName(bep.getBudget().getId(), ModelTestData.getElementProvider1().getName());

        assertThat(actual).isNull();
    }

    @Test
    public void findAllElementProviders() {
        BudgetElementProvider bep = getSavedForTestCleanBudgetElementProvider();
        ElementProvider provider1 = ModelTestData.getElementProvider1();
        ElementProvider provider2 = ModelTestData.getElementProvider2();
        bService.addElementProvider(bep.getBudget().getId(), provider1);
        bService.addElementProvider(bep.getBudget().getId(), provider2);

        assertThat(bService.findAllElementProviders(bep.getBudget().getId()).size() == 2).isTrue();
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
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());

        bService.addOperation(savedBudget.getId(), op1);

        assertThat(bService.findAllOperations(savedBudget.getId()).size() == 1).isTrue();
    }

    @Test
    public void addOperation() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        Operation op2 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());

        assertThat(bService.findAllOperations(savedBudget.getId()).size() == 0).isTrue();

        bService.addOperation(savedBudget.getId(), op1);
        assertThat(bService.findAllOperations(savedBudget.getId()).size() == 1).isTrue();

        bService.addOperation(savedBudget.getId(), op2);
        assertThat(bService.findAllOperations(savedBudget.getId()).size() == 2).isTrue();
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
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());

        Operation incomeOp = bService.createOperation(user, incomeEl, provider, incomePrice);
        Operation consumptionOp = bService.createOperation(user, consumptionEl, provider, consumptionPrice);

        bService.addOperation(budget.getId(), incomeOp);
        budget = bService.findBudgetById(budget.getId());
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(0.5));

        bService.addOperation(budget.getId(), consumptionOp);
        budget = bService.findBudgetById(budget.getId());
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(-2.5));
    }

    @Test(expected = CurrenciesNotEqualsRuntimeException.class)
    public void whenCurrenciesNotEqualsAddOperationThrowException() {
        Budget budget = ModelTestData.getBudget1();
        budget.getBalance().setCurrencies(Currencies.PLN);
        bService.saveBudget(budget);

        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op1.getPrice().setCurrencies(Currencies.EUR);

        bService.addOperation(budget.getId(), op1);
    }

    private Operation getSavedOperationForCheckRuntimeException() {
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());
        return bService.createOperation(user, element, provider, ModelTestData.getPrice1());
    }

    @Test(expected = ValidationException.class)
    public void whenUserInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getUser().setName("");

        bService.addOperation(savedBudget.getId(), op);
    }

    @Test(expected = ValidationException.class)
    public void whenElementInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getElement().setName("");

        bService.addOperation(savedBudget.getId(), op);
    }

    @Test(expected = ValidationException.class)
    public void whenElementProviderInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getElementProvider().setName("");

        bService.addOperation(savedBudget.getId(), op);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenUserIdInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getUser().setId(0);

        bService.addOperation(savedBudget.getId(), op);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenElementIdInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getElement().setId(0);

        bService.addOperation(savedBudget.getId(), op);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenElementProviderIdInvalidAddOperationThrowException() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        Operation op = getSavedOperationForCheckRuntimeException();

        op.getElementProvider().setId(0);

        bService.addOperation(savedBudget.getId(), op);
    }

    @Test
    public void removeOperation() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        bService.addOperation(savedBudget.getId(), op1);
        assertThat(bService.findAllOperations(savedBudget.getId()).size() == 1).isTrue();

        bService.removeOperation(savedBudget.getId(), op1.getId());
        assertThat(bService.findAllOperations(savedBudget.getId()).size() == 0).isTrue();
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
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());

        Operation incomeOp = bService.createOperation(user, incomeEl, provider, incomePrice);
        Operation consumptionOp = bService.createOperation(user, consumptionEl, provider, consumptionPrice);

        bService.addOperation(budget.getId(), incomeOp);
        bService.addOperation(budget.getId(), consumptionOp);
        budget = bService.findBudgetById(budget.getId());
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(-2.5));

        bService.removeOperation(budget.getId(), incomeOp.getId());
        budget = bService.findBudgetById(budget.getId());
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(-3.0));

        bService.removeOperation(budget.getId(), consumptionOp.getId());
        budget = bService.findBudgetById(budget.getId());
        assertThat(budget.getBalance().getAmount()).isEqualTo(BigDecimal.valueOf(0.0));
    }

    @Test(expected = CurrenciesNotEqualsRuntimeException.class)
    public void whenCurrenciesNotEqualsRemoveOperationThrowException() {
        Budget budget = ModelTestData.getBudget1();
        budget.getBalance().setCurrencies(Currencies.PLN);
        Budget savedBudget = bService.saveBudget(budget);
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        bService.addOperation(savedBudget.getId(), op1);
        op1.getPrice().setCurrencies(Currencies.EUR);

        bService.removeOperation(savedBudget.getId(), op1.getId());
    }

    @Test
    public void findOperationByElement() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element forSearch = eRepository.save(ModelTestData.getElement1());
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, forSearch, provider, ModelTestData.getPrice1());
        Operation op2 = bService.createOperation(user, forSearch, provider, ModelTestData.getPrice1());
        Operation op3 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());

        bService.addOperation(budget.getId(), op1);
        bService.addOperation(budget.getId(), op2);
        bService.addOperation(budget.getId(), op3);

        assertThat(bService.findOperationsByElement(budget.getId(), forSearch.getId()).size() == 2).isTrue();
    }

    @Test
    public void findOperationByElementProvider() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider1 = epRepository.save(ModelTestData.getElementProvider1());
        ElementProvider provider2 = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user, element, provider1, ModelTestData.getPrice1());
        Operation op2 = bService.createOperation(user, element, provider1, ModelTestData.getPrice1());
        Operation op3 = bService.createOperation(user, element, provider2, ModelTestData.getPrice1());

        bService.addOperation(budget.getId(), op1);
        bService.addOperation(budget.getId(), op2);
        bService.addOperation(budget.getId(), op3);

        assertThat(bService.findOperationsByElementProvider(budget.getId(), provider1.getId()).size() == 2).isTrue();
    }

    @Test
    public void findOperationsByUser() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user1 = uService.saveUser(ModelTestData.getUser1());
        User user2 = uService.saveUser(ModelTestData.getUser2());
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation op1 = bService.createOperation(user1, element, provider, ModelTestData.getPrice1());
        Operation op2 = bService.createOperation(user1, element, provider, ModelTestData.getPrice1());
        Operation op3 = bService.createOperation(user2, element, provider, ModelTestData.getPrice1());

        bService.addOperation(budget.getId(), op1);
        bService.addOperation(budget.getId(), op2);
        bService.addOperation(budget.getId(), op3);

        assertThat(bService.findOperationsByUser(budget.getId(), user1.getId()).size() == 2).isTrue();
    }

    @Test
    public void findOperationsBetweenDates() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now().plusDays(1);

        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op1.setCreateOn(from.plusSeconds(2));
        Operation op2 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op2.setCreateOn(from.plusHours(2));
        Operation op3 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op3.setCreateOn(from.minusDays(12));

        bService.addOperation(budget.getId(), op1);
        bService.addOperation(budget.getId(), op2);
        bService.addOperation(budget.getId(), op3);

        assertThat(bService.findOperationsBetweenDates(budget.getId(), from, to).size() == 2).isTrue();
    }

    @Test(expected = IntervalRuntimeException.class)
    public void whenPeriodInvalidFindOperationBetweenDatesThrowException() {
        Budget budget = ModelTestData.getBudget1();
        budget.setId(2);
        LocalDateTime to = LocalDateTime.now().minusDays(1);
        LocalDateTime from = LocalDateTime.now().plusDays(1);

        bService.findOperationsBetweenDates(budget.getId(), from, to);
    }

    @Test
    public void findOperationsBetweenPrices() {
        Budget budget = bService.saveBudget(ModelTestData.getBudget1());
        User user = uService.saveUser(ModelTestData.getUser1());
        Element element = eRepository.save(ModelTestData.getElement1());
        ElementProvider provider = epRepository.save(ModelTestData.getElementProvider1());
        Price from = ModelTestData.getPrice1();
        from.setAmount(BigDecimal.TEN);
        Price to = ModelTestData.getPrice1();
        to.setAmount(from.getAmount().add(from.getAmount()));

        Operation op1 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op1.getPrice().setAmount(from.getAmount().add(BigDecimal.valueOf(0.01)));
        Operation op2 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op2.getPrice().setAmount(to.getAmount().subtract(BigDecimal.valueOf(0.01)));
        Operation op3 = bService.createOperation(user, element, provider, ModelTestData.getPrice1());
        op3.getPrice().setAmount(BigDecimal.ZERO);

        bService.addOperation(budget.getId(), op1);
        bService.addOperation(budget.getId(), op2);
        bService.addOperation(budget.getId(), op3);

        assertThat(bService.findOperationsBetweenPrices(budget.getId(), from, to).size() == 2).isTrue();
    }

    @Test(expected = IntervalRuntimeException.class)
    public void whenIntervalInvalidFindOperationsBetweenPricesThrowException() {
        Budget budget = ModelTestData.getBudget1();
        budget.setId(1);
        Price to = ModelTestData.getPrice1();
        Price from = ModelTestData.getPrice1();
        from.setAmount(BigDecimal.TEN);
        to.setAmount(BigDecimal.ZERO);

        bService.findOperationsBetweenPrices(budget.getId(), from, to);
    }
}