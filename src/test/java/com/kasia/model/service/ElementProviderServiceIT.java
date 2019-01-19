package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.IdInvalidRuntimeException;
import com.kasia.model.ElementProvider;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElementProviderServiceIT {
    @Autowired
    private ElementProviderService elementProviderService;

    @After
    public void after() {
        for (ElementProvider provider : elementProviderService.findAll()) elementProviderService.delete(provider);
    }

    @Test
    public void create() throws Exception {
        ElementProvider provider = ModelTestData.getElementProvider1();
        ElementProvider create = elementProviderService.create(provider.getName(), provider.getDescription());

        assertThat(create).isEqualTo(provider);
    }

    @Test(expected = ValidationException.class)
    public void whenElementProviderInvalidCreateThrowException() throws Exception {
        ElementProvider provider = ModelTestData.getElementProvider1();
        elementProviderService.create("", provider.getDescription());
    }

    @Test
    public void saveNew() {
        ElementProvider expected = ModelTestData.getElementProvider1();
        assertThat(expected.getId() == 0).isTrue();

        elementProviderService.save(expected);
        ElementProvider actual = elementProviderService.findById(expected.getId());

        assertThat(expected.getId() > 0).isTrue();
        assertThat(actual).isNotNull();
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test(expected = ValidationException.class)
    public void whenElementProviderInvalidSaveThrowException() {
        ElementProvider expected = ModelTestData.getElementProvider1();
        expected.setName("");
        elementProviderService.save(expected);
    }

    @Test
    public void saveUpdate() {
        ElementProvider provider = ModelTestData.getElementProvider1();
        elementProviderService.save(provider);
        String newName = "newName";
        long id = provider.getId();

        provider.setName(newName);
        elementProviderService.save(provider);

        assertThat(elementProviderService.findById(id).getName()).isEqualTo(newName);
    }

    @Test
    public void findAll() {
        assertThat(elementProviderService.findAll().size() == 0).isTrue();
        elementProviderService.save(ModelTestData.getElementProvider1());
        elementProviderService.save(ModelTestData.getElementProvider2());

        assertThat(elementProviderService.findAll().size() == 2).isTrue();
    }

    @Test
    public void delete() {
        ElementProvider provider = ModelTestData.getElementProvider1();
        elementProviderService.save(provider);

        elementProviderService.delete(provider);

        assertThat(elementProviderService.findById(provider.getId())).isNull();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenIdZeroDeleteThrowException() {
        ElementProvider provider = ModelTestData.getElementProvider1();
        provider.setId(0);
        elementProviderService.delete(provider);
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenIdNegativeDeleteThrowException() {
        ElementProvider provider = ModelTestData.getElementProvider1();
        provider.setId(-1);
        elementProviderService.delete(provider);
    }

    @Test(expected = ValidationException.class)
    public void whenElementProviderInvalidDeleteThrowException() {
        ElementProvider provider = ModelTestData.getElementProvider1();
        provider.setName("");
        elementProviderService.delete(provider);
    }

    @Test
    public void findById() {
        ElementProvider provider = ModelTestData.getElementProvider1();

        elementProviderService.save(provider);

        assertThat(elementProviderService.findById(provider.getId())).isEqualTo(provider);
        assertThat(elementProviderService.findById(provider.getId() + 1)).isNull();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenFindByIdWithZeroIdThenException() {
        assertThat(elementProviderService.findById(0)).isNotNull();
    }

    @Test(expected = IdInvalidRuntimeException.class)
    public void whenFindByIdWithNegativeIdThenException() {
        assertThat(elementProviderService.findById(-1)).isNotNull();
    }
}