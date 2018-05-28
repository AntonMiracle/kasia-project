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

import java.util.Set;

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
            service.delete(name1);
        }

        result = service.isNameExist(name2);
        if (!result.isCalculationFailed() && result.getResult()) {
            service.delete(name2);
        }
    }

    // SAVE ===========================================================
    @Test
    public void saveUniqueSuccess() {
        role = new Role();
        role.setName(name1);

        Result<Role> result = service.save(role);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult().getId()).isNotNull();
    }

    @Test
    public void saveNotUniqueFailed() {
        role = new Role();
        role.setName(name1);

        Result<Role> result = service.save(role);
        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult()).isNotNull();

        role = new Role();
        role.setName(name1);

        result = service.save(role);
        assertThat(result.isCalculationFailed()).isTrue();
        assertThat(result.getResult()).isNull();
    }

    @Test(expected = NullPointerException.class)
    public void whenSaveNullThenNPE() {
        service.save(null);
    }

    // UPDATE ============================================================================
    @Test
    public void updateExistSuccess() {
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
    public void updateNotExistFailed() {
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

    // GET by ID ======================================================
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

    // DELETE by ID ================================================
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
    public void deleteByNotExistIdFalse() {
        Result<Boolean> resultBoolean = service.delete(-1L);
        assertThat(resultBoolean.isCalculationFailed()).isFalse();

        assertThat(resultBoolean.getResult()).isFalse();
    }

    @Test(expected = NullPointerException.class)
    public void whenDeleteByIdNullThenNPE() {
        service.delete((Long) null);
    }

    // GET ALL ======================================================
    @Test
    public void getAllSuccess() {
        Result<Set<Role>> resultAll = service.get();
        assertThat(resultAll.isCalculationFailed()).isFalse();
        long size = resultAll.getResult().size();

        role = new Role();
        role.setName(name1);
        Result<Role> result = service.save(role);
        assertThat(result.isCalculationFailed()).isFalse();

        resultAll = service.get();
        assertThat(resultAll.isCalculationFailed()).isFalse();
        long newSize = resultAll.getResult().size();

        assertThat(newSize).isEqualTo(size + 1);
    }

    @Test
    public void whenZeroModelsGetAllReturnEmptySet() {
        Result<Set<Role>> resultAll = service.get();

        assertThat(resultAll).isNotNull();
        assertThat(resultAll.isCalculationFailed()).isFalse();

        assertThat(resultAll.getResult().size()).isEqualTo(0);
    }

    // DELETE by NAME =======================================
    @Test
    public void deleteByExistNameSuccess() {
        role = new Role();
        role.setName(name1);

        Result<Role> result = service.save(role);
        assertThat(result.isCalculationFailed()).isFalse();

        String name = result.getResult().getName();
        Result<Boolean> resultBoolean = service.delete(name);
        assertThat(result.isCalculationFailed()).isFalse();

        assertThat(resultBoolean.getResult()).isTrue();
    }

    @Test
    public void deleteByNotExistNameFailed() {
        assertThat(service.get(name1).isCalculationFailed()).isTrue();

        Result<Boolean> resultBoolean = service.delete(name1);

        assertThat(resultBoolean.isCalculationFailed()).isTrue();
        assertThat(resultBoolean.getResult()).isNull();
    }

    @Test(expected = NullPointerException.class)
    public void whenDeleteByNameNullThenNPE() {
        service.delete((String) null);
    }

    // GET by NAME =================================================
    @Test
    public void getByExistNameSuccess() {
        role = new Role();
        role.setName(name1);
        Result<Role> result = service.save(role);
        assertThat(result.isCalculationFailed()).isFalse();

        result = service.get(name1);
        assertThat(result.isCalculationFailed()).isFalse();

        assertThat(result.getResult().getName()).isEqualTo(name1);
    }

    @Test
    public void getByNotExistNameFalse() {
        Result<Role> result = service.get(name1);
        assertThat(result.isCalculationFailed()).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void whenGetByNameNullThenNPE() {
        service.get((String) null);
    }


    // IS NAME EXIST ================================================
    @Test
    public void isNameExistReturnTrueWhenNameExist() {
        role = new Role();
        role.setName(name1);
        assertThat(service.save(role).isCalculationFailed()).isFalse();

        Result<Boolean> result = service.isNameExist(name1);
        assertThat(result.isCalculationFailed()).isFalse();

        assertThat(result.getResult()).isTrue();
    }

    @Test
    public void isNameExistReturnFalseWhenNameNotExist() {
        Result<Boolean> result = service.isNameExist(name1);
        assertThat(result.isCalculationFailed()).isFalse();

        assertThat(result.getResult()).isFalse();
    }

    @Test(expected = NullPointerException.class)
    public void isNameExistWithNull() {
        service.isNameExist(null);
    }

}