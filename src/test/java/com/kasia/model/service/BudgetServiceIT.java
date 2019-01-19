package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.model.*;
import com.kasia.model.repository.BudgetElementProviderRepository;
import com.kasia.model.repository.BudgetElementRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
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

    @After
    public void cleanData() {
        beRepository.findAll().forEach(beRepository::delete);
        bepRepository.findAll().forEach(bepRepository::delete);

        bService.findAllBudgets().forEach(bService::deleteBudget);
        eService.findAll().forEach(eService::delete);
        epService.findAll().forEach(epService::delete);
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
}