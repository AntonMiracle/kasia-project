package com.kasia.service;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Employer;
import org.junit.After;
import org.junit.Test;

import javax.inject.Inject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EmployerServiceIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private EmployerService employerService;
    private final String NAME = "name";
    private final String NAME_2 = "name2";

    @After
    public void after() {
        for (Employer e : employerService.getAllEmployers()) {
            employerService.delete(e.getId());
        }
    }

    @Test
    public void create() throws Exception {
        Employer employer = employerService.create(NAME);

        assertThat(employer.getId() > 0).isTrue();
        assertThat(employer.getName()).isEqualTo(NAME);
        assertThat(employer.getDescription()).isNotNull();
    }

    @Test
    public void delete() throws Exception {
        long id = employerService.create(NAME).getId();

        assertThat(employerService.getEmployerById(id)).isNotNull();
        assertThat(employerService.delete(id)).isTrue();
        assertThat(employerService.getEmployerById(id)).isNull();
    }

    @Test
    public void update() throws Exception {
        Employer employer = employerService.create(NAME);

        employer.setName(NAME_2);
        employer = employerService.update(employer);

        assertThat(employer.getName()).isEqualTo(NAME_2);
    }

    @Test
    public void getEmployerById() throws Exception {
        Employer employer = employerService.create(NAME);

        assertThat(employerService.getEmployerById(employer.getId())).isEqualTo(employer);
    }

    @Test
    public void getEmployerByName() throws Exception {
        Employer employer = employerService.create(NAME);

        assertThat(employerService.getEmployerByName(employer.getName())).isEqualTo(employer);
    }

    @Test
    public void getAllEmployers() throws Exception {
        assertThat(employerService.getAllEmployers().size() == 0);

        employerService.create(NAME);
        employerService.create(NAME_2);

        assertThat(employerService.getAllEmployers().size() == 2).isTrue();
    }
}