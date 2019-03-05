package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.model.*;
import com.kasia.model.repository.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
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
    private UserConnectBudgetRepository ucbRepository;
    @Autowired
    private OperationService oService;

    @After
    public void cleanData() {
        beRepository.findAll().forEach(beRepository::delete);
        bepRepository.findAll().forEach(bepRepository::delete);
        ucbRepository.findAll().forEach(ucbRepository::delete);

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
        Provider provider = epRepository.save(ModelTestData.getElementProvider1());
        Operation operation = oService.createOperation(user, element, provider, ModelTestData.getPrice1());

        bService.addElement(budget.getId(), element);
        bService.addElementProvider(budget.getId(), provider);
        oService.addOperation(budget.getId(), operation);

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
    public void findBudgetById() {
        Budget expected = ModelTestData.getBudget1();
        bService.saveBudget(expected);

        Budget actual = bService.findBudgetById(expected.getId());

        assertThat(actual).isEqualTo(expected);
        assertThat(bService.findBudgetById(0)).isNull();
        assertThat(bService.findBudgetById(-1)).isNull();
    }

    @Test
    public void findAllBudgets() {
        assertThat(bService.findAllBudgets().size() == 0).isTrue();

        bService.saveBudget(ModelTestData.getBudget1());
        bService.saveBudget(ModelTestData.getBudget1());

        assertThat(bService.findAllBudgets().size() == 2).isTrue();
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

    @Test
    public void findElementByName() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element expected = ModelTestData.getElement1();
        bService.addElement(be.getBudget().getId(), expected);

        Element actual = bService.findElementByName(be.getBudget().getId(), expected.getName());

        assertThat(actual).isEqualTo(expected);
        assertThat(actual).isEqualTo(expected);
        assertThat(bService.findElementByName(0, "label.name")).isNull();
        assertThat(bService.findElementByName(-1, "label.name")).isNull();
        assertThat(bService.findElementByName(0, null)).isNull();
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
        assertThat(bService.addElement(0, null)).isFalse();
        assertThat(bService.addElement(-1, null)).isFalse();
        assertThat(bService.addElement(0, element1)).isFalse();

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

    private int countElementsInBudget(Budget budget) {
        return beRepository.findByBudgetId(budget.getId())
                .map(budgetElement -> budgetElement.getElements().size())
                .orElse(0);
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
    public void findAllElements() {
        BudgetElement be = getSavedForTestCleanBudgetElement();
        Element element1 = ModelTestData.getElement1();
        Element element2 = ModelTestData.getElement2();
        bService.addElement(be.getBudget().getId(), element1);
        bService.addElement(be.getBudget().getId(), element2);

        assertThat(bService.findAllElements(be.getBudget().getId()).size() == 2).isTrue();
        assertThat(bService.findAllElements(0).size() == 0).isTrue();
        assertThat(bService.findAllElements(-1).size() == 0).isTrue();
    }

    private BudgetProvider getSavedForTestCleanBudgetElementProvider() {
        Budget savedBudget = bService.saveBudget(ModelTestData.getBudget1());
        BudgetProvider be = ModelTestData.getBudgetElementProvider1();
        be.getProviders().clear();
        be.setBudget(savedBudget);
        return bepRepository.save(be);
    }

    @Test
    public void elementProviderUnique() {
        BudgetProvider bep = getSavedForTestCleanBudgetElementProvider();
        Provider savedProvider = epRepository.save(ModelTestData.getElementProvider1());
        bep.getProviders().add(savedProvider);
        bepRepository.save(bep);

        assertThat(bService.isElementProviderUnique(bep.getBudget().getId(), savedProvider.getName())).isFalse();
        assertThat(bService.isElementProviderUnique(bep.getBudget().getId(), ModelTestData.getElementProvider2().getName())).isTrue();
        assertThat(bService.isElementProviderUnique(0, null)).isFalse();
        assertThat(bService.isElementProviderUnique(-1, null)).isFalse();
    }

    @Test
    public void findElementProviderByName() {
        BudgetProvider bep = getSavedForTestCleanBudgetElementProvider();
        Provider expected = ModelTestData.getElementProvider1();
        bService.addElementProvider(bep.getBudget().getId(), expected);

        Provider actual = bService.findElementProviderByName(bep.getBudget().getId(), expected.getName());

        assertThat(actual).isEqualTo(expected);
        assertThat(bService.findElementProviderByName(0, null)).isNull();
        assertThat(bService.findElementProviderByName(-1, null)).isNull();
    }

    @Test
    public void addElementProvider() {
        BudgetProvider bep = getSavedForTestCleanBudgetElementProvider();
        Provider provider1 = ModelTestData.getElementProvider1();
        Provider provider2 = ModelTestData.getElementProvider1();
        provider2.setName(provider1.getName() + "new");
        Provider provider3 = ModelTestData.getElementProvider1();
        int providersBeforeAdd = countElementProvidersInBudget(bep.getBudget());

        assertThat(provider1.getName()).isEqualTo(provider3.getName());
        assertThat(bService.addElementProvider(bep.getBudget().getId(), provider1)).isTrue();
        assertThat(bService.addElementProvider(bep.getBudget().getId(), provider2)).isTrue();
        assertThat(bService.addElementProvider(bep.getBudget().getId(), provider3)).isFalse();
        assertThat(bService.addElementProvider(0, provider3)).isFalse();
        assertThat(bService.addElementProvider(-1, null)).isFalse();
        assertThat(bService.addElementProvider(1, null)).isFalse();

        int providersAfterAdd = countElementProvidersInBudget(bep.getBudget());
        assertThat(providersAfterAdd == (providersBeforeAdd + 2)).isTrue();
    }

    @Test
    public void addElementProviderAddItToElementProviderRepository() {
        BudgetProvider bep = getSavedForTestCleanBudgetElementProvider();
        Provider provider = ModelTestData.getElementProvider1();

        Set<Provider> elementsBefore = new HashSet<>();
        epRepository.findAll().forEach(elementsBefore::add);

        bService.addElementProvider(bep.getBudget().getId(), provider);

        Set<Provider> elementsAfter = new HashSet<>();
        epRepository.findAll().forEach(elementsAfter::add);

        assertThat(elementsAfter.size() == (elementsBefore.size() + 1)).isTrue();
    }

    @Test
    public void removeElementProvider() {
        BudgetProvider bep = getSavedForTestCleanBudgetElementProvider();
        Provider provider = ModelTestData.getElementProvider1();
        bService.addElementProvider(bep.getBudget().getId(), provider);
        int providersBeforeRemove = countElementProvidersInBudget(bep.getBudget());

        assertThat(bService.removeElementProvider(bep.getBudget().getId(), provider.getId())).isTrue();
        assertThat(bService.removeElementProvider(0, 0)).isFalse();
        assertThat(bService.removeElementProvider(-1, -1)).isFalse();

        int providersAfterRemove = countElementProvidersInBudget(bep.getBudget());
        assertThat(providersAfterRemove == (providersBeforeRemove - 1)).isTrue();
    }

    @Test
    public void removeElementProviderRemoveItFromElementProviderRepository() {
        BudgetProvider bep = getSavedForTestCleanBudgetElementProvider();
        Provider provider = ModelTestData.getElementProvider1();
        bService.addElementProvider(bep.getBudget().getId(), provider);

        Set<Provider> elementsBefore = new HashSet<>();
        epRepository.findAll().forEach(elementsBefore::add);

        bService.removeElementProvider(bep.getBudget().getId(), provider.getId());

        Set<Provider> elementsAfter = new HashSet<>();
        epRepository.findAll().forEach(elementsAfter::add);

        assertThat(elementsAfter.size() == (elementsBefore.size() - 1)).isTrue();
    }

    private int countElementProvidersInBudget(Budget budget) {
        return bepRepository.findByBudgetId(budget.getId())
                .map(bep -> bep.getProviders().size())
                .orElse(0);
    }

    @Test
    public void findAllElementProviders() {
        BudgetProvider bep = getSavedForTestCleanBudgetElementProvider();
        Provider provider1 = ModelTestData.getElementProvider1();
        Provider provider2 = ModelTestData.getElementProvider2();
        bService.addElementProvider(bep.getBudget().getId(), provider1);
        bService.addElementProvider(bep.getBudget().getId(), provider2);

        assertThat(bService.findAllElementProviders(bep.getBudget().getId()).size() == 2).isTrue();
        assertThat(bService.findAllElementProviders(0).size() == 0).isTrue();
        assertThat(bService.findAllElementProviders(-1).size() == 0).isTrue();
    }
}