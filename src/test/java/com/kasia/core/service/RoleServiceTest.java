package com.kasia.core.service;

import com.kasia.config.AppConfig;
import com.kasia.core.TestHelper;
import com.kasia.core.model.Role;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class RoleServiceTest implements TestHelper<Role> {
    @Autowired
    private RoleService service;
    private Role role;

    @Before
    public void before() {
        assert service != null;
        role = new Role();
    }

    @After
    public void after() {
        if (service.isNameExist(getRoleNameForTesting1())) {
            service.delete(getRoleNameForTesting1());
        }
        if (service.isNameExist(getRoleNameForTesting2())) {
            service.delete(getRoleNameForTesting2());
        }
    }

    // SAVE OR UPDATE --------------------------------------------------------
    @Test
    public void saveOrUpdate() {
        role.setName(getRoleNameForTesting1());
        assertThat(role.getId()).isNull();

        role = service.saveOrUpdate(role);
        assertThat(role.getId()).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSaveOrUpdateWithNotUniqueNameThenIAE() {
        role.setName(getRoleNameForTesting1());
        service.saveOrUpdate(role);

        role = new Role();
        role.setName(getRoleNameForTesting1());
        service.saveOrUpdate(role);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenSaveOrUpdateWithInValidNameThenCVE() {
        role.setName("");
        assertThat(role.getId()).isNull();

        role = service.saveOrUpdate(role);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenSaveOrUpdateWithNullNameThenCVE() {
        role.setName(null);
        assertThat(role.getId()).isNull();

        role = service.saveOrUpdate(role);
    }

    @Test(expected = NullPointerException.class)
    public void whenSaveOrUpdateWithNullThenNPE() {
        service.saveOrUpdate(null);
    }

    // GET by ID --------------------------------------------------------
    @Test
    public void getById() {
        role.setName(getRoleNameForTesting1());
        Long id = service.saveOrUpdate(role).getId();

        role = service.get(id);

        assertThat(role.getId()).isEqualTo(id);
    }

    @Test(expected = NullPointerException.class)
    public void whenGetByIdWithNullThenNPE() {
        service.get((Long) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetByIdWithNotExistIdThenIAE() {
        service.get(10L);
    }

    // DELETE by ID --------------------------------------------------------
    @Test
    public void deleteById() {
        role.setName(getRoleNameForTesting1());
        Long id = service.saveOrUpdate(role).getId();

        assertThat(service.delete(id)).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void whenDeleteByIdWithNullThenNPE() {
        service.delete((Long) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDeleteByIdWithNotExistIdThenIAE() {
        service.delete(-10L);
    }

    // GET ALL --------------------------------------------------------
    @Test
    public void getAll() {
        int size = service.get().size();

        role.setName(getRoleNameForTesting1());
        service.saveOrUpdate(role).getName();

        assertThat(service.get().size()).isEqualTo(size + 1);
    }

    // DELETE by NAME --------------------------------------------------------
    @Test
    public void deleteByName() {
        role.setName(getRoleNameForTesting1());
        String name = service.saveOrUpdate(role).getName();

        assertThat(service.delete(name)).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void whenDeleteByNameWithNullThenNPE() {
        service.delete((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDeleteByNameWithNotExistNameThenIAE() {
        service.delete("-10L");
    }

    // GET by NAME--------------------------------------------------------
    @Test
    public void getByName() {
        role.setName(getRoleNameForTesting1());
        String name = service.saveOrUpdate(role).getName();

        role = service.get(name);

        assertThat(role.getName()).isEqualTo(name);
    }

    @Test(expected = NullPointerException.class)
    public void whenGetGroupTypeByNameWithNullThenNPE() {
        service.get((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetGroupTypeByNameWithNotExistNameThenIAE() {
        service.get("-10L");
    }

    // IS NAME EXIST--------------------------------------------------------
    @Test
    public void isNameExistWithExistingName() {
        role.setName(getRoleNameForTesting1());
        service.saveOrUpdate(role).getName();

        assertThat(service.isNameExist(getRoleNameForTesting1())).isTrue();
    }

    @Test
    public void isNameExistWithNonExistingName() {
        assertThat(service.isNameExist(getRoleNameForTesting1())).isFalse();
    }

    @Test(expected = NullPointerException.class)
    public void isNameExistWithNull() {
        service.isNameExist(null);
    }

}