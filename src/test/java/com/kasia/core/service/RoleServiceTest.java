package com.kasia.core.service;

import com.kasia.config.AppConfig;
import com.kasia.core.TestHelper;
import com.kasia.core.model.Result;
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
    private String name1;
    private String name2;

    @Before
    public void before() {
        assert service != null;
        name1 = getRoleNameForTesting1();
        name2 = getRoleNameForTesting2();
    }

    @After
    public void after() {
        Result<Boolean> result = service.isNameExist(name1);
        if (!result.isCalculationFailed() && result.getResult()) {
            service.delete(getRoleNameForTesting1());
        }

        result = service.isNameExist(name2);
        if (!result.isCalculationFailed() && result.getResult()) {
            service.delete(getRoleNameForTesting2());
        }
    }

    // SAVE ===========================================================
    @Test
    public void saveUniqueRoleSuccess() {
        role = new Role();
        role.setName(name1);

        Result<Role> result = service.save(role);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult().getId()).isNotNull();
    }

    @Test
    public void saveNotUniqueRoleFailed() {
        role = new Role();
        role.setName(name1);

        Result<Role> result = service.save(role);
        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult()).isNull();

        role = new Role();
        role.setName(name1);

        result = service.save(role);
        assertThat(result.isCalculationFailed()).isTrue();
        assertThat(result.getResult()).isNotNull();
    }

    @Test(expected = NullPointerException.class)
    public void whenSaveNullThenNPE() {
        service.save(null);
    }

    // UPDATE ============================================================================
    @Test
    public void updateExistRoleSuccess() {
        role = new Role();
        role.setName(name1);

        Result<Role> result = service.save(role);
        assertThat(result.isCalculationFailed()).isFalse();

        role = result.getResult();
        role.setName(name2);

        result = service.update(role);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult().getId()).isEqualTo(role.getId());
    }

    @Test
    public void updateNotExistRoleFailed() {
        role = new Role();
        role.setName(name1);

        Result<Role> result = service.update(role);

        assertThat(result.isCalculationFailed()).isTrue();
        assertThat(result.getResult()).isNull();
    }

    @Test(expected = NullPointerException.class)
    public void whenUpdateNullThenNPE() {
        service.update(null);
    }

    // GET by ID --------------------------------------------------------
    @Test
    public void getByExistIdSuccess() {
        role = new Role();
        role.setName(name1);

        Result<Role> result = service.save(role);
        assertThat(result.isCalculationFailed()).isFalse();

        long id = result.getResult().getId();

        result = service.get(id);
        assertThat(result.isCalculationFailed()).isFalse();

        assertThat(result.getResult().getId()).isEqualTo(id);
    }

    @Test
    public void getByNotExistIdFailed() {
        Result<Role> result = service.get(-1L);
        assertThat(result.isCalculationFailed()).isTrue();
        assertThat(result.getResult()).isNull();

        result = service.get(0L);
        assertThat(result.isCalculationFailed()).isTrue();
        assertThat(result.getResult()).isNull();

    }

    @Test(expected = NullPointerException.class)
    public void whenGetByIdNullThenNPE() {
        service.get((Long) null);
    }

    // DELETE by ID --------------------------------------------------------
    @Test
    public void deleteByExistIdSuccess() {
        role = new Role();
        role.setName(name1);
        Result<Role> result = service.save(role);
        assertThat(result.isCalculationFailed()).isFalse();

        long id = result.getResult().getId();

        Result<Boolean> resultBoolean = service.delete(id);
        assertThat(resultBoolean.isCalculationFailed()).isFalse();

        assertThat(resultBoolean.getResult()).isTrue();
    }

    @Test
    public void deleteByNotExistIdFailed() {
        Result<Boolean> resultBoolean = service.delete(-1L);
        assertThat(resultBoolean.isCalculationFailed()).isFalse();

        assertThat(resultBoolean.getResult()).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void whenDeleteByIdNullThenNPE() {
        service.delete((Long) null);
    }

    // GET ALL --------------------------------------------------------
    @Test
    public void getAll() {
        int size = service.get().size();

        role.setName(getRoleNameForTesting1());
        service.save(role).getName();

        assertThat(service.get().size()).isEqualTo(size + 1);
    }

    // DELETE by NAME --------------------------------------------------------
    @Test
    public void deleteByName() {
        role.setName(getRoleNameForTesting1());
        String name = service.save(role).getName();

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
        String name = service.save(role).getName();

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
        service.save(role).getName();

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