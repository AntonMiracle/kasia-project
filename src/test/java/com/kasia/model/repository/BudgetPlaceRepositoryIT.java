package com.kasia.model.repository;

import com.kasia.ModelTestData;
import com.kasia.model.BudgetPlace;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BudgetPlaceRepositoryIT {
    @Autowired
    private BudgetPlaceRepository repository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private BudgetRepository budgetRepository;

    @After
    public void cleanData() {
        repository.findAll().forEach(model -> repository.delete(model));
        budgetRepository.findAll().forEach(model -> budgetRepository.delete(model));
        placeRepository.findAll().forEach(model -> placeRepository.delete(model));
    }

    @Test
    public void save() {
        BudgetPlace budgetPlace = ModelTestData.budgetProvider();
        assertThat(budgetPlace.getId() == 0).isTrue();

        saveForTest(budgetPlace);

        assertThat(budgetPlace.getId() > 0).isTrue();
    }

    private BudgetPlace saveForTest(BudgetPlace budgetPlace) {
        budgetPlace.getPlaces().forEach(placeRepository::save);
        budgetRepository.save(budgetPlace.getBudget());
        repository.save(budgetPlace);
        return budgetPlace;
    }

    @Test
    public void findById() throws Exception {
        BudgetPlace budgetPlace = saveForTest(ModelTestData.budgetProvider());
        long id = budgetPlace.getId();

        budgetPlace = repository.findById(id).get();

        assertThat(budgetPlace).isNotNull();
        assertThat(budgetPlace.getId()).isEqualTo(id);
    }

    @Test
    public void delete() throws Exception {
        BudgetPlace budgetPlace = saveForTest(ModelTestData.budgetProvider());

        repository.delete(budgetPlace);

        assertThat(repository.findById(budgetPlace.getId()).isPresent()).isFalse();
    }

    @Test
    public void findAll() throws Exception {
        saveForTest(ModelTestData.budgetProvider());
        saveForTest(ModelTestData.budgetProvider());
        Set<BudgetPlace> budgetPlaces = new HashSet<>();

        repository.findAll().forEach(budgetPlaces::add);

        assertThat(budgetPlaces.size() == 2).isTrue();
    }

    @Test
    public void findByBudgetId() {
        BudgetPlace expected = saveForTest(ModelTestData.budgetProvider());

        Optional<BudgetPlace> actual = repository.findByBudgetId(expected.getBudget().getId());

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }
}