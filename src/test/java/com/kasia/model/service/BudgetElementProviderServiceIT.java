package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.IdRuntimeException;
import com.kasia.model.Budget;
import com.kasia.model.BudgetElementProvider;
import com.kasia.model.ElementProvider;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetElementProviderServiceIT {
    @Autowired
    private BudgetElementProviderService bepService;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private ElementProviderService providerService;

    @After
    public void after() {
        bepService.findAll().forEach(bepService::delete);
    }

    @Test
    public void create() throws Exception {
        BudgetElementProvider bep = ModelTestData.getBudgetElementProvider1();
        BudgetElementProvider actual = bepService.create(bep.getBudget());

        assertThat(actual).isNotNull();
        assertThat(actual.getBudget()).isEqualTo(bep.getBudget());
        assertThat(actual.getElementProviders()).isNotNull();
        assertThat(actual.getId() == 0).isTrue();
    }


    @Test
    public void addElementProvider() throws Exception {
    }

    @Test
    public void removeElementProvider() throws Exception {
    }

    private BudgetElementProvider saveForTest(BudgetElementProvider bep) {
        budgetService.save(bep.getBudget());
        for (ElementProvider provider : bep.getElementProviders()) {
            providerService.save(provider);
        }
        return bepService.save(bep);
    }

    @Test
    public void findByBudgetId() throws Exception {
        BudgetElementProvider expected = ModelTestData.getBudgetElementProvider1();
        saveForTest(expected);

        BudgetElementProvider actual = bepService.findByBudgetId(expected.getBudget().getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findElementProviderByName() throws Exception {
        BudgetElementProvider bep = saveForTest(ModelTestData.getBudgetElementProvider1());
        for (ElementProvider expected : bep.getElementProviders()) {
            ElementProvider actual = bepService.findElementProviderByName(bep.getBudget(), expected.getName());
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Test
    public void isElementProviderNameUnique() throws Exception {
        BudgetElementProvider bep = saveForTest(ModelTestData.getBudgetElementProvider1());
        String name = null;
        for (ElementProvider provider : bep.getElementProviders()) name = provider.getName();

        assertThat(name).isNotNull();
        assertThat(bepService.isElementProviderNameUnique(bep.getBudget(), name));
        assertThat(bepService.isElementProviderNameUnique(bep.getBudget(), name + name));
    }

    @Test
    public void saveNew() {
        BudgetElementProvider expected = ModelTestData.getBudgetElementProvider1();
        assertThat(expected.getId() == 0).isTrue();

        saveForTest(expected);

        BudgetElementProvider actual = bepService.findById(expected.getId());
        assertThat(expected.getId() > 0).isTrue();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void saveUpdate() {
        BudgetElementProvider expected = saveForTest(ModelTestData.getBudgetElementProvider1());
        ElementProvider newProvider = ModelTestData.getElementProvider1();
        String newName = newProvider.getName() + newProvider.getName();

        int oldSize = expected.getElementProviders().size();
        newProvider.setName(newName);
        providerService.save(newProvider);
        assertThat(bepService.isElementProviderNameUnique(expected.getBudget(), newName)).isTrue();

        expected.getElementProviders().add(newProvider);
        bepService.save(expected);

        BudgetElementProvider actual = bepService.findById(expected.getId());
        assertThat(actual).isEqualTo(expected);
        assertThat(bepService.isElementProviderNameUnique(expected.getBudget(), newName)).isFalse();
        assertThat(actual.getElementProviders().size()).isEqualTo(oldSize + 1);
    }

    @Test
    public void findAll() {
        assertThat(bepService.findAll().size() == 0).isTrue();
        saveForTest(ModelTestData.getBudgetElementProvider1());
        saveForTest(ModelTestData.getBudgetElementProvider2());

        assertThat(bepService.findAll().size() == 2).isTrue();
    }

    @Test
    public void delete() {
        BudgetElementProvider expected = saveForTest(ModelTestData.getBudgetElementProvider1());

        bepService.delete(expected);

        assertThat(bepService.findById(expected.getId())).isNull();
    }

    @Test
    public void deleteInTransaction() {
        BudgetElementProvider expected = saveForTest(ModelTestData.getBudgetElementProvider1());
        assertThat(bepService.findAll().size() == 1).isTrue();
        Budget budget = expected.getBudget();
        int providersSize = expected.getElementProviders().size();

        for (ElementProvider provider : expected.getElementProviders()) {
            provider.setId(0);
            break;
        }
        try {
            bepService.delete(expected);
        } catch (RuntimeException ex) {

        }

        assertThat(providerService.findAll().size() == providersSize).isTrue();
        assertThat(budgetService.findById(budget.getId())).isNotNull();
        assertThat(bepService.findAll().size() == 1).isTrue();
    }

    @Test(expected = IdRuntimeException.class)
    public void whenDeleteWithZeroIdThenException() {
        BudgetElementProvider expected = ModelTestData.getBudgetElementProvider1();
        expected.setId(0);
        bepService.delete(expected);
    }

    @Test(expected = IdRuntimeException.class)
    public void whenDeleteWithNegativeIdThenException() {
        BudgetElementProvider expected = ModelTestData.getBudgetElementProvider1();
        expected.setId(-1);
        bepService.delete(expected);
    }

    @Test
    public void findById() {
        BudgetElementProvider expected = saveForTest(ModelTestData.getBudgetElementProvider1());

        assertThat(bepService.findById(expected.getId())).isEqualTo(expected);
        assertThat(bepService.findById(expected.getId() + 1)).isNull();
    }

    @Test(expected = IdRuntimeException.class)
    public void whenFindByIdWithZeroIdThenException() {
        assertThat(bepService.findById(0)).isNotNull();
    }

    @Test(expected = IdRuntimeException.class)
    public void whenFindByIdWithNegativeIdThenException() {
        assertThat(bepService.findById(-1)).isNotNull();
    }


}