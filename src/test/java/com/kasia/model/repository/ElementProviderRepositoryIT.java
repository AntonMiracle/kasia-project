package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.ElementProvider;
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
public class ElementProviderRepositoryIT {
    @Autowired
    private ElementProviderRepository repository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
    }

    @Test
    public void save() {
        ElementProvider elementProvider = ModelTestData.getElementProvider1();
        assertThat(elementProvider.getId() == 0).isTrue();

        saveForTest(elementProvider);

        assertThat(elementProvider.getId() > 0).isTrue();
    }

    private ElementProvider saveForTest(ElementProvider elementProvider) {
        repository.save(elementProvider);
        return elementProvider;
    }

    @Test
    public void getById() throws Exception {
        ElementProvider elementProvider = saveForTest(ModelTestData.getElementProvider1());
        long id = elementProvider.getId();

        elementProvider = repository.findById(id).get();

        assertThat(elementProvider).isNotNull();
        assertThat(elementProvider.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        ElementProvider elementProvider = ModelTestData.getElementProvider1();

        repository.delete(elementProvider);

        assertThat(repository.findById(elementProvider.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestData.getElementProvider1());
        saveForTest(ModelTestData.getElementProvider2());
        Set<ElementProvider> providers = new HashSet<>();

        repository.findAll().forEach(providers::add);

        assertThat(providers.size() == 2).isTrue();
    }

}