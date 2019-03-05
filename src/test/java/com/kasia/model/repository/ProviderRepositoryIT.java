package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.Provider;
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
public class ProviderRepositoryIT {
    @Autowired
    private ElementProviderRepository repository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
    }

    @Test
    public void save() {
        Provider provider = ModelTestData.getElementProvider1();
        assertThat(provider.getId() == 0).isTrue();

        saveForTest(provider);

        assertThat(provider.getId() > 0).isTrue();
    }

    private Provider saveForTest(Provider provider) {
        repository.save(provider);
        return provider;
    }

    @Test
    public void getById() throws Exception {
        Provider provider = saveForTest(ModelTestData.getElementProvider1());
        long id = provider.getId();

        provider = repository.findById(id).get();

        assertThat(provider).isNotNull();
        assertThat(provider.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        Provider provider = ModelTestData.getElementProvider1();

        repository.delete(provider);

        assertThat(repository.findById(provider.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {
        saveForTest(ModelTestData.getElementProvider1());
        saveForTest(ModelTestData.getElementProvider2());
        Set<Provider> providers = new HashSet<>();

        repository.findAll().forEach(providers::add);

        assertThat(providers.size() == 2).isTrue();
    }

}