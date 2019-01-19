package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.model.Element;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElementServiceIT {
    @Autowired
    private ElementService elementService;

    @After
    public void cleanData() {
        for (Element element : elementService.findAll()) elementService.delete(element);
    }

    @Test
    public void create() throws Exception {
        Element element = ModelTestData.getElement1();
        Element create = elementService.create(element.getName(), element.getType(), element.getDefaultPrice());

        assertThat(create).isEqualTo(element);
    }

    @Test
    public void saveNew() {
        Element expected = ModelTestData.getElement1();
        assertThat(expected.getId() == 0).isTrue();

        elementService.save(expected);
        Element actual = elementService.findById(expected.getId());

        assertThat(expected.getId() > 0).isTrue();
        assertThat(actual).isNotNull();
        assertThat(actual.getDefaultPrice()).isEqualTo(expected.getDefaultPrice());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getType()).isEqualTo(expected.getType());
    }

    @Test
    public void saveUpdate() {
        Element element = ModelTestData.getElement1();
        elementService.save(element);
        String newName = "newName";
        long id = element.getId();

        element.setName(newName);
        elementService.save(element);

        assertThat(elementService.findById(id).getName()).isEqualTo(newName);
    }

    @Test
    public void findAll() {
        assertThat(elementService.findAll().size() == 0).isTrue();
        elementService.save(ModelTestData.getElement1());
        elementService.save(ModelTestData.getElement2());

        assertThat(elementService.findAll().size() == 2).isTrue();
    }

    @Test
    public void delete() {
        Element element = ModelTestData.getElement1();
        elementService.save(element);

        elementService.delete(element);

        assertThat(elementService.findById(element.getId())).isNull();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenDeleteWithZeroIdThenException() {
        Element element = ModelTestData.getElement1();
        element.setId(0);
        elementService.delete(element);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenDeleteWithNegativeIdThenException() {
        Element element = ModelTestData.getElement1();
        element.setId(-1);
        elementService.delete(element);
    }

    @Test
    public void findById() {
        Element element = ModelTestData.getElement1();

        elementService.save(element);

        assertThat(elementService.findById(element.getId())).isEqualTo(element);
        assertThat(elementService.findById(element.getId() + 1)).isNull();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenFindByIdWithZeroIdThenException() {
        assertThat(elementService.findById(0)).isNotNull();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenFindByIdWithNegativeIdThenException() {
        assertThat(elementService.findById(-1)).isNotNull();
    }

}