package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.model.Operation;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationServiceIT {

    @Autowired
    private OperationService operationService;
    @Autowired
    private ElementService elementService;
    @Autowired
    private ElementProviderService providerService;
    @Autowired
    private UserService userService;

    @After
    public void cleanData() {
        operationService.findAll().forEach(model -> operationService.delete(model));
        elementService.findAll().forEach(model -> elementService.delete(model));
        providerService.findAll().forEach(model -> providerService.delete(model));
        userService.findAll().forEach(model -> userService.delete(model));
    }

    @Test
    public void saveNew() {
        Operation expected = ModelTestData.getOperation1();

        saveForTest(expected);

        Operation actual = operationService.findById(expected.getId());
        assertThat(actual).isNotNull();
        assertThat(actual.getUser()).isEqualTo(expected.getUser());
        assertThat(actual.getElement()).isEqualTo(expected.getElement());
        assertThat(actual.getElementProvider()).isEqualTo(expected.getElementProvider());
        assertThat(actual.getCreateOn().compareTo(LocalDateTime.now().plusSeconds(2)) < 0).isTrue();
        assertThat(actual.getId() > 0).isTrue();
    }

    @Test
    public void saveDuplication() {
        assertThat(operationService.findAll().size() == 0).isTrue();
        Operation expected = ModelTestData.getOperation1();

        int index = 4;
        int count = 0;
        while (index-- > 0) {
            expected.setId(0);
            saveForTest(expected);
            count++;
        }
        assertThat(operationService.findAll().size() == count).isTrue();
    }

    @Test
    public void doNotSaveInvalid() {
        Operation expected = ModelTestData.getOperation1();
        userService.save(expected.getUser());
        expected.getUser().setId(0);

        try {
            operationService.save(expected);
        } catch (RuntimeException ex) {

        }

        assertThat(operationService.findAll().size() == 0).isTrue();
        assertThat(userService.findAll().size() == 1).isTrue();
        assertThat(elementService.findAll().size() == 0).isTrue();
        assertThat(providerService.findAll().size() == 0).isTrue();
    }

    private Operation saveForTest(Operation operation) {
        userService.save(operation.getUser());
        elementService.save(operation.getElement());
        providerService.save(operation.getElementProvider());
        return operationService.save(operation);
    }

    @Test
    public void create() throws Exception {
        Operation expected = ModelTestData.getOperation1();

        Operation actual = operationService.create(expected.getUser(), expected.getElement(), expected.getPrice(), expected.getElementProvider());

        assertThat(actual).isNotNull();
        assertThat(actual.getCreateOn().compareTo(LocalDateTime.now().plusSeconds(3)) < 0).isTrue();
        assertThat(actual.getElementProvider()).isEqualTo(expected.getElementProvider());
        assertThat(actual.getElement()).isEqualTo(expected.getElement());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getUser()).isEqualTo(expected.getUser());
    }

    @Test
    public void delete() {
        Operation operation = saveForTest(ModelTestData.getOperation1());

        operationService.delete(operation);

        assertThat(operationService.findById(operation.getId())).isNull();
    }

    @Test
    public void deleteNotCascade() {
        Operation operation = saveForTest(ModelTestData.getOperation1());

        operationService.delete(operation);

        assertThat(userService.findById(operation.getUser().getId())).isNotNull();
        assertThat(elementService.findById(operation.getElement().getId())).isNotNull();
        assertThat(providerService.findById(operation.getElementProvider().getId())).isNotNull();
    }

    @Test
    public void findById() {
        Operation expected = saveForTest(ModelTestData.getOperation1());

        Operation actual = operationService.findById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findAll() {
        saveForTest(ModelTestData.getOperation1());
        saveForTest(ModelTestData.getOperation2());
        Set<Operation> operations = new HashSet<>();

        operationService.findAll().forEach(operations::add);

        assertThat(operations.size() == 2).isTrue();
    }

}