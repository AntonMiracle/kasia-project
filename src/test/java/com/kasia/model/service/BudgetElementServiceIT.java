package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.model.BudgetElement;
import com.kasia.model.Element;
import com.kasia.model.ElementType;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetElementServiceIT {
    @Autowired
    private BudgetElementService budgetElementService;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private ElementService elementService;

    @After
    public void cleanData() {
        budgetElementService.findAll().forEach(budgetElementService::delete);
    }

    @Test
    public void saveNew() {
        BudgetElement expected = ModelTestData.getBudgetElement1();

        saveForTest(expected);

        BudgetElement actual = budgetElementService.findById(expected.getId());
        assertThat(actual).isEqualTo(expected);
    }

    private BudgetElement saveForTest(BudgetElement budgetElement) {
        budgetService.save(budgetElement.getBudget());
        budgetElement.getElements().forEach(elementService::save);
        return budgetElementService.save(budgetElement);
    }

    @Test
    public void create() throws Exception {
        BudgetElement expected = ModelTestData.getBudgetElement1();
        BudgetElement actual = budgetElementService.create(expected.getBudget());

        assertThat(actual.getBudget()).isEqualTo(expected.getBudget());
        assertThat(actual.getElements()).isNotNull();
        assertThat(actual.getId() == 0).isTrue();
    }

    @Test
    public void delete() {
        BudgetElement expected = saveForTest(ModelTestData.getBudgetElement1());
        assertThat(budgetElementService.findAll().size() == 1).isTrue();

        budgetElementService.delete(expected);

        assertThat(budgetElementService.findAll().size() == 0).isTrue();
    }

    @Test
    public void findById() {
        BudgetElement expected = saveForTest(ModelTestData.getBudgetElement1());

        BudgetElement actual = budgetElementService.findById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findAll() {
        assertThat(budgetElementService.findAll().size() == 0).isTrue();
        saveForTest(ModelTestData.getBudgetElement1());
        saveForTest(ModelTestData.getBudgetElement2());

        assertThat(budgetElementService.findAll().size() == 2).isTrue();
    }

    @Test
    public void addElement() throws Exception {
        BudgetElement expected = ModelTestData.getBudgetElement1();
        expected.setElements(new HashSet<>());
        saveForTest(expected);
        assertThat(budgetElementService.findById(expected.getId()).getElements().size() == 0).isTrue();

        Element element = ModelTestData.getElement1();
        elementService.save(element);

        budgetElementService.addElement(expected.getBudget(), element);

        assertThat(budgetElementService.findById(expected.getId()).getElements().size() == 1).isTrue();
    }

    @Test
    public void removeElement() throws Exception {
        Element element = ModelTestData.getElement1();
        elementService.save(element);
        Set<Element> elements = new HashSet<>();
        elements.add(element);

        BudgetElement expected = ModelTestData.getBudgetElement1();
        expected.setElements(elements);
        saveForTest(expected);
        assertThat(budgetElementService.findById(expected.getId()).getElements().size() == 1).isTrue();


        budgetElementService.removeElement(expected.getBudget(), element);

        assertThat(budgetElementService.findById(expected.getId()).getElements().size() == 0).isTrue();
    }

    @Test
    public void findByBudgetId() throws Exception {
        BudgetElement expected = saveForTest(ModelTestData.getBudgetElement1());

        BudgetElement actual = budgetElementService.findByBudgetId(expected.getBudget().getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findElementByName() throws Exception {
        BudgetElement expected = ModelTestData.getBudgetElement1();
        Element element = ModelTestData.getElement2();
        expected.getElements().add(element);
        saveForTest(expected);

        assertThat(budgetElementService.findElementByName(expected.getBudget(), element.getName())).isEqualTo(element);
    }

    @Test
    public void findByElementType() throws Exception {
        Set<Element> elements = new HashSet<>();
        Element element1 = ModelTestData.getElement1();
        element1.setType(ElementType.CONSUMPTION);
        Element element2 = ModelTestData.getElement2();
        element2.setType(ElementType.INCOME);
        Element element3 = ModelTestData.getElement2();
        element3.setType(ElementType.INCOME);
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);

        BudgetElement expected = ModelTestData.getBudgetElement1();
        expected.setElements(elements);
        saveForTest(expected);

        assertThat(budgetElementService.findByElementType(expected.getBudget(), ElementType.CONSUMPTION).size() == 1).isTrue();
    }

    @Test
    public void isElementNameUnique() throws Exception {
        Set<Element> elements = new HashSet<>();
        Element element1 = ModelTestData.getElement1();
        elements.add(element1);

        BudgetElement expected = ModelTestData.getBudgetElement1();
        expected.setElements(elements);
        saveForTest(expected);

        assertThat(budgetElementService.isElementNameUnique(expected.getBudget(), element1.getName())).isFalse();
        assertThat(budgetElementService.isElementNameUnique(expected.getBudget(), element1.getName() + "www")).isTrue();
    }

}