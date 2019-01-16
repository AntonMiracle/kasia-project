package com.kasia.model.service;

import com.kasia.ModelTestData;
import com.kasia.exception.IdRuntimeException;
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

    @Test(expected = IdRuntimeException.class)
    public void whenDeleteWithZeroIdThenException() {
        ElementProvider provider = ModelTestData.getElementProvider1();
        provider.setId(0);
        elementProviderService.delete(provider);
    }

    @Test(expected = IdRuntimeException.class)
    public void whenDeleteWithNegativeIdThenException() {
        ElementProvider provider = ModelTestData.getElementProvider1();
        provider.setId(-1);
        elementProviderService.delete(provider);
    }

    @Test
    public void findById() {
        ElementProvider provider = ModelTestData.getElementProvider1();

        elementProviderService.save(provider);

        assertThat(elementProviderService.findById(provider.getId())).isEqualTo(provider);
        assertThat(elementProviderService.findById(provider.getId() + 1)).isNull();
    }

    @Test(expected = IdRuntimeException.class)
    public void whenFindByIdWithZeroIdThenException() {
        assertThat(elementProviderService.findById(0)).isNotNull();
    }

    @Test(expected = IdRuntimeException.class)
    public void whenFindByIdWithNegativeIdThenException() {
        assertThat(elementProviderService.findById(-1)).isNotNull();
    }
}