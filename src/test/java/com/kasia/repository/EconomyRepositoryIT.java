package com.kasia.repository;

import com.kasia.model.Budget;
import com.kasia.model.Economy;
import com.oneandone.ejbcdiunit.EjbUnitRunner;
import com.oneandone.ejbcdiunit.persistence.TestPersistenceFactory;
import org.jglue.cdiunit.AdditionalClasses;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(EjbUnitRunner.class)
@AdditionalClasses({EconomyRepository.class, TestPersistenceFactory.class})
public class EconomyRepositoryIT {
    @EJB
    private EconomyRepository economyRepository;
    private final LocalDateTime CREATE_ON = LocalDateTime.of(2020, 10, 10, 10, 10, 10);
    private final String NAME = "name";
    private final String NAME_2 = "name2";

    @After
    public void after() {
        for (Economy e : economyRepository.getAll()) {
            economyRepository.delete(e);
        }
    }

    @Test
    public void getById() throws Exception {
        Economy economy = new Economy(NAME, CREATE_ON);
        economy.setBudgets(new HashSet<>());
        long id = economyRepository.save(economy).getId();

        assertThat(economyRepository.getById(id)).isEqualTo(economy);
    }

    @Test
    public void getAll() throws Exception {
        Economy economy = new Economy(NAME, CREATE_ON);
        economy.setBudgets(new HashSet<>());
        Economy economy1 = new Economy(NAME, CREATE_ON);
        economy.setBudgets(new HashSet<>());
        economyRepository.save(economy);
        economyRepository.save(economy1);

        assertThat(economyRepository.getAll().size() == 2).isTrue();
    }

    @Test
    public void delete() throws Exception {
        Economy economy = new Economy(NAME, CREATE_ON);
        economy.setBudgets(new HashSet<>());
        long id = economyRepository.save(economy).getId();

        assertThat(economyRepository.getById(id)).isNotNull();
        assertThat(economyRepository.delete(economyRepository.getById(id))).isTrue();
        assertThat(economyRepository.getById(id)).isNull();
    }

    @Test
    public void save() throws Exception {
        Economy expected = new Economy(NAME, CREATE_ON);
        expected.setBudgets(new HashSet<>());

        long id = economyRepository.save(expected).getId();
        Economy actual = economyRepository.getById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        Economy economy = new Economy(NAME, CREATE_ON);
        economy.setBudgets(new HashSet<>());
        long id = economyRepository.save(economy).getId();
        economy = economyRepository.getById(id);

        assertThat(economy.getBudgets().size() == 0).isTrue();
        economy.setName(NAME_2);
        Budget budget = new Budget(NAME, BigDecimal.TEN, Currency.getInstance("EUR"), CREATE_ON);
        budget.setArticles(new HashSet<>());
        economy.getBudgets().add(budget);
        economyRepository.save(economy);

        economy = economyRepository.getById(id);
        for (Budget b : economy.getBudgets()) {
            assertThat(b.getId() > 0).isTrue();
        }
        assertThat(economyRepository.getById(id).getName()).isEqualTo(NAME_2);
        assertThat(economyRepository.getById(id).getBudgets().size() == 1).isTrue();
    }
}