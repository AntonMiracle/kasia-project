package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.model.BudgetOperation;
import com.kasia.model.Operation;
import com.kasia.model.Price;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetOperationServiceIT {
    @Autowired
    private BudgetOperationService boService;
    @Autowired
    private BudgetService bService;
    @Autowired
    private OperationService oService;
    @Autowired
    private UserService uService;
    @Autowired
    private ElementService eService;
    @Autowired
    private ElementProviderService epService;

    @After
    public void cleanData() {
        boService.findAll().forEach(boService::delete);
        oService.findAll().forEach(oService::delete);
        uService.findAll().forEach(uService::delete);
        eService.findAll().forEach(eService::delete);
        epService.findAll().forEach(epService::delete);
    }

    @Test
    public void saveNew() {
        BudgetOperation expected = ModelTestData.getBudgetOperation1();

        saveForTest(expected);

        assertThat(boService.findById(expected.getId())).isEqualTo(expected);
    }

    @Test
    public void findAll() {
        assertThat(boService.findAll().size() == 0).isTrue();

        saveForTest(ModelTestData.getBudgetOperation1());
        saveForTest(ModelTestData.getBudgetOperation2());

        assertThat(boService.findAll().size() == 2).isTrue();
    }

    @Test
    public void findById() {
        BudgetOperation expected = ModelTestData.getBudgetOperation1();
        saveForTest(expected);

        assertThat(boService.findById(expected.getId())).isEqualTo(expected);
    }

    @Test
    public void delete() {
        BudgetOperation expected = saveForTest(ModelTestData.getBudgetOperation1());

        boService.delete(expected);

        assertThat(boService.findAll().size() == 0).isTrue();
    }

    @Test
    public void create() throws Exception {
        BudgetOperation expected = ModelTestData.getBudgetOperation1();

        BudgetOperation actual = boService.create(expected.getBudget());

        assertThat(actual.getBudget()).isEqualTo(expected.getBudget());
        assertThat(actual.getOperations().size() == 0).isTrue();
    }

    private BudgetOperation saveForTest(BudgetOperation bo) {
        for (Operation operation : bo.getOperations()) {
            uService.save(operation.getUser());
            eService.save(operation.getElement());
            epService.save(operation.getElementProvider());
            oService.save(operation);
        }
        bService.save(bo.getBudget());
        boService.save(bo);
        return bo;
    }

    @Test
    public void addOperation() throws Exception {
        BudgetOperation expected = ModelTestData.getBudgetOperation1();
        Operation operation = configurationOperationForFindOperationTests(expected);
        expected.setOperations(new HashSet<>());
        saveForTest(expected);

        boService.addOperation(expected.getBudget(), operation);

        assertThat(boService.findById(expected.getId()).getOperations().size() == 1).isTrue();
        assertThat(boService.findById(expected.getId()).getOperations().contains(operation)).isTrue();
    }

    @Test
    public void removeOperation() throws Exception {
        BudgetOperation expected = ModelTestData.getBudgetOperation1();
        Operation operation = configurationOperationForFindOperationTests(expected);
        expected.getOperations().clear();
        expected.getOperations().add(operation);
        saveForTest(expected);

        boService.removeOperation(expected.getBudget(), operation);

        assertThat(boService.findById(expected.getId()).getOperations().size() == 0).isTrue();
        assertThat(boService.findById(expected.getId()).getOperations().contains(operation)).isFalse();
    }

    @Test
    public void findByBudgetId() throws Exception {
        BudgetOperation expected = ModelTestData.getBudgetOperation1();
        Operation operation = configurationOperationForFindOperationTests(expected);

        assertThat(boService.findByBudgetId(expected.getBudget().getId())).isEqualTo(expected);

    }

    @Test
    public void findOperationsByElementName() throws Exception {
        BudgetOperation budgetOperation = ModelTestData.getBudgetOperation1();
        Operation expected = configurationOperationForFindOperationTests(budgetOperation);
        saveForTest(budgetOperation);
        String elementName = expected.getElement().getName();

        Set<Operation> actual = boService.findOperationsByElementName(budgetOperation.getBudget(), elementName);
        assertThat(actual.contains(expected)).isTrue();
        assertThat(actual.size() == 1).isTrue();
    }

    private Operation configurationOperationForFindOperationTests(BudgetOperation bo) {
        Operation expected = ModelTestData.getOperation1();
        bo.getOperations().clear();
        bo.getOperations().add(expected);
        saveForTest(bo);
        return expected;
    }

    @Test
    public void findOperationByElementProviderName() throws Exception {
        BudgetOperation budgetOperation = ModelTestData.getBudgetOperation1();
        Operation expected = configurationOperationForFindOperationTests(budgetOperation);
        saveForTest(budgetOperation);
        String elementName = expected.getElementProvider().getName();

        Set<Operation> actual = boService.findOperationsByElementName(budgetOperation.getBudget(), elementName);
        assertThat(actual.contains(expected)).isTrue();
        assertThat(actual.size() == 1).isTrue();
    }

    @Test
    public void findOperationsBetweenDate() throws Exception {
        BudgetOperation budgetOperation = ModelTestData.getBudgetOperation1();
        Operation expected = configurationOperationForFindOperationTests(budgetOperation);
        saveForTest(budgetOperation);
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now().plusDays(1);

        Set<Operation> actual = boService.findOperationsBetweenDate(budgetOperation.getBudget(), from, to);

        assertThat(actual.size() == 1).isTrue();
        assertThat(actual.contains(expected)).isTrue();
    }

    @Test
    public void findOperationsBetweenPrice() throws Exception {
        BudgetOperation budgetOperation = ModelTestData.getBudgetOperation1();
        Operation expected = configurationOperationForFindOperationTests(budgetOperation);
        saveForTest(budgetOperation);
        Price from = ModelTestData.getPrice1();
        from.setAmount(expected.getPrice().getAmount().subtract(BigDecimal.TEN));
        Price to = ModelTestData.getPrice1();
        to.setAmount(expected.getPrice().getAmount().add(BigDecimal.TEN));

        Set<Operation> actual = boService.findOperationsBetweenPrice(budgetOperation.getBudget(), from, to);
    }

}