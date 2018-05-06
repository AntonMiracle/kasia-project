package com.kasia.core.repository;

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
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class RoleRepositoryTest implements TestHelper<Role> {
    @Autowired
    private RoleRepository repository;
    private Role role;
    private String name1;
    private String name2;

    @Before
    public void before() {
        assert repository != null;
        role = new Role();
        name1 = getRoleNameForTesting1();
        name2 = getRoleNameForTesting2();
    }

    @After
    @Transactional
    public void after() {
        if (repository.get(name1) != null) {
            repository.delete(repository.get(name1).getId());
        }
        if (repository.get(name2) != null) {
            repository.delete(repository.get(name2).getId());
        }
    }

    @Test
    @Transactional
    public void saveRole() {
        role.setName(name1);
        assertThat(role.getId()).isNull();

        role = repository.saveOrUpdate(role);
        assertThat(role.getId()).isNotNull();
    }

    @Test
    @Transactional
    public void getRoleById() {
        role.setName(name1);
        Long id = repository.saveOrUpdate(role).getId();

        assertThat(repository.get(id).getId()).isEqualTo(id);
    }

    @Test
    @Transactional
    public void deleteRoleById() {
        role.setName(name1);
        Long id = repository.saveOrUpdate(role).getId();

        assertThat(repository.delete(id)).isTrue();
    }

    @Test
    @Transactional
    public void getAllRoles() {
        int initSize = repository.get().size();
        role.setName(name1);
        repository.saveOrUpdate(role);

        assertThat(repository.get().size()).isEqualTo(initSize + 1);
    }

    @Test
    @Transactional
    public void getByName() {
        role.setName(name1);
        Long id = repository.saveOrUpdate(role).getId();

        assertThat(repository.get(name1).getId()).isEqualTo(id);
    }

    @Test
    @Transactional
    public void isNameExistWithExistingName() {
        role.setName(name1);
        repository.saveOrUpdate(role).getId();

        assertThat(repository.isNameExist(name1)).isTrue();
    }

}