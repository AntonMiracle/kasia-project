package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.Place;
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
public class PlaceRepositoryIT {
    @Autowired
    private PlaceRepository repository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
    }

    @Test
    public void save() {
        Place place = ModelTestData.provider();
        assertThat(place.getId() == 0).isTrue();

        saveForTest(place);

        assertThat(place.getId() > 0).isTrue();
    }

    private Place saveForTest(Place place) {
        repository.save(place);
        return place;
    }

    @Test
    public void findById() throws Exception {
        Place place = saveForTest(ModelTestData.provider());
        long id = place.getId();

        place = repository.findById(id).get();

        assertThat(place).isNotNull();
        assertThat(place.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        Place place = ModelTestData.provider();

        repository.delete(place);

        assertThat(repository.findById(place.getId()).isPresent()).isFalse();
    }

    @Test
    public void findAll() throws Exception {
        saveForTest(ModelTestData.provider());
        saveForTest(ModelTestData.provider());
        Set<Place> places = new HashSet<>();

        repository.findAll().forEach(places::add);

        assertThat(places.size() == 2).isTrue();
    }

}