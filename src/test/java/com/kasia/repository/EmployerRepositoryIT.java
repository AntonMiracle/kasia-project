package com.kasia.repository;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Employer;
import org.junit.After;
import org.junit.Test;

import javax.ejb.EJB;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EmployerRepositoryIT extends ConfigurationEjbCdiContainerForIT {
    @EJB
    private EmployerRepository employerRepository;
    private final String NAME = "name";
    private final String NAME_2 = "name2";
    private final String DESCRIPTION = "Some description";

    @After
    public void after() {
        for (Employer e : employerRepository.getAll()) {
            employerRepository.delete(e);
        }
    }

    @Test
    public void getById() throws Exception {
        Employer expected = new Employer(NAME);
        expected.setDescription(DESCRIPTION);
        long id = employerRepository.save(expected).getId();

        assertThat(employerRepository.getById(id)).isEqualTo(expected);
    }

    @Test
    public void getAll() throws Exception {
        assertThat(employerRepository.getAll().size() == 0).isTrue();

        employerRepository.save(new Employer(NAME));
        employerRepository.save(new Employer(NAME));
        employerRepository.save(new Employer(NAME));

        assertThat(employerRepository.getAll().size() == 3).isTrue();
    }

    @Test
    public void delete() throws Exception {
        long id = employerRepository.save(new Employer(NAME)).getId();

        assertThat(employerRepository.getById(id)).isNotNull();
        assertThat(employerRepository.delete(employerRepository.getById(id))).isTrue();
        assertThat(employerRepository.getById(id)).isNull();
    }

    @Test
    public void save() throws Exception {
        Employer expected = new Employer(NAME);
        expected.setDescription(DESCRIPTION);

        long id = employerRepository.save(expected).getId();
        Employer actual = employerRepository.getById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        Employer employer = new Employer(NAME);
        employer.setDescription(DESCRIPTION);
        long id = employerRepository.save(employer).getId();
        employer = employerRepository.getById(id);

        employer.setName(NAME_2);
        employerRepository.save(employer);

        assertThat(employerRepository.getById(id).getName()).isEqualTo(NAME_2);
    }

    @Test
    public void getByName() throws Exception {
        assertThat(employerRepository.getByName(NAME)).isNull();

        employerRepository.save(new Employer(NAME));
        assertThat(employerRepository.getByName(NAME)).isNotNull();
    }
}