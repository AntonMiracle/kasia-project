package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.Element;
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
public class ElementRepositoryIT {
    @Autowired
    private ElementRepository repository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
    }

    @Test
    public void save() {
        Element element = ModelTestData.getElement1();
        assertThat(element.getId() == 0).isTrue();

        saveForTest(element);

        assertThat(element.getId() > 0).isTrue();
    }

    private Element saveForTest(Element element) {
        repository.save(element);
        return element;
    }

    @Test
    public void getById() throws Exception {
        Element element = saveForTest(ModelTestData.getElement1());
        long id = element.getId();

        element = repository.findById(id).get();

        assertThat(element).isNotNull();
        assertThat(element.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        Element element = saveForTest(ModelTestData.getElement1());

        repository.delete(element);

        assertThat(repository.findById(element.getId()).isPresent()).isFalse();
    }

    @Test
    public void getAll() throws Exception {

        saveForTest(ModelTestData.getElement1());
        saveForTest(ModelTestData.getElement2());
        Set<Element> elements = new HashSet<>();

        repository.findAll().forEach(elements::add);

        assertThat(elements).isNotNull();
        assertThat(elements.size() == 2).isTrue();
    }
}