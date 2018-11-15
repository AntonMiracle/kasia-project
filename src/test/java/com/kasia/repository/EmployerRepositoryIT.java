package com.kasia.repository;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Employer;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EmployerRepositoryIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private EmployerRepository repository;
    private final String NAME = "name";
    private final String NAME_2 = "name2";
    private final String DESCRIPTION = "Some description";

    @After
    public void after() {
        for (Employer e : repository.getAll()) {
            repository.delete(e);
        }
    }

    @Test
    public void getById() throws Exception {
        Employer expected = new Employer(NAME, DESCRIPTION);
        long id = repository.save(expected).getId();

        assertThat(repository.getById(id)).isEqualTo(expected);
    }

    @Test
    public void getAll() throws Exception {
        assertThat(repository.getAll().size() == 0).isTrue();

        repository.save(new Employer(NAME, DESCRIPTION));
        repository.save(new Employer(NAME, DESCRIPTION));
        repository.save(new Employer(NAME, DESCRIPTION));

        assertThat(repository.getAll().size() == 3).isTrue();
    }

    @Test
    public void delete() throws Exception {
        long id = repository.save(new Employer(NAME, DESCRIPTION)).getId();

        assertThat(repository.getById(id)).isNotNull();
        assertThat(repository.delete(repository.getById(id))).isTrue();
        assertThat(repository.getById(id)).isNull();
    }

    @Test
    public void save() throws Exception {
        Employer expected = new Employer(NAME, DESCRIPTION);

        long id = repository.save(expected).getId();
        Employer actual = repository.getById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        Employer employer = new Employer(NAME, DESCRIPTION);
        long id = repository.save(employer).getId();
        employer = repository.getById(id);

        employer.setName(NAME_2);
        repository.save(employer);

        assertThat(repository.getById(id).getName()).isEqualTo(NAME_2);
    }

    @Test
    public void getByName() throws Exception {
        assertThat(repository.getByName(NAME)).isNull();

        repository.save(new Employer(NAME, DESCRIPTION));
        assertThat(repository.getByName(NAME)).isNotNull();
    }
}