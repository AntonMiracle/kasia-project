package com.kasia.core.service;

import com.kasia.config.AppConfig;
import com.kasia.core.TestHelper;
import com.kasia.core.model.GroupType;
import com.kasia.core.model.Result;
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
public class GroupTypeServiceTest implements TestHelper<GroupType> {
    @Autowired
    private GroupTypeService service;
    private GroupType groupType;
    private String name1;
    private String name2;

    @Before
    public void before() {
        assert service != null;
        name1 = getGroupTypeNameForTesting1();
        name2 = getGroupTypeNameForTesting2();
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

    // SAVE ==================================================================
    @Test
    public void saveUniqueSuccess() {
        groupType = new GroupType();
        groupType.setName(name1);
        Result<GroupType> result = service.save(groupType);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult().getId()).isNotNull();
    }

    @Test
    public void saveNotUniqueFailed() {
        groupType = new GroupType();
        groupType.setName(name1);

        Result<GroupType> result = service.save(groupType);
        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult()).isNull();

        groupType = new GroupType();
        groupType.setName(name1);

        result = service.save(groupType);
        assertThat(result.isCalculationFailed()).isTrue();
        assertThat(result.getResult()).isNotNull();
    }


    @Test(expected = NullPointerException.class)
    public void whenSaveNullThenNPE() {
        service.save(null);
    }

    // UPDATE =======================================================
    @Test
    public void updateExistSuccess() {
        groupType = new GroupType();
        groupType.setName(name1);

        Result<GroupType> result = service.save(groupType);
        assertThat(result.isCalculationFailed()).isFalse();

        groupType = result.getResult();
        groupType.setName(name2);

        result = service.update(groupType);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult().getId()).isEqualTo(groupType.getId());
    }

    @Test
    public void updateNotExistFailed() {
        groupType = new GroupType();
        groupType.setName(name1);

        Result<GroupType> result = service.update(groupType);

        assertThat(result.isCalculationFailed()).isTrue();
        assertThat(result.getResult()).isNull();
    }

    @Test(expected = NullPointerException.class)
    public void whenUpdateNullThenNPE() {
        service.update(null);
    }

    // GET by ID ================================================================
    @Test
    public void getByExistIdSuccess() {
        groupType = new GroupType();
        groupType.setName(name1);

        Result<GroupType> result = service.save(groupType);
        assertThat(result.isCalculationFailed()).isFalse();

        long id = result.getResult().getId();

        result = service.get(id);
        assertThat(result.isCalculationFailed()).isFalse();

        assertThat(result.getResult().getId()).isEqualTo(id);
    }

    @Test
    public void getByNotExistIdFailed() {
        Result<GroupType> result = service.get(-1L);
        assertThat(result.isCalculationFailed()).isTrue();
        assertThat(result.getResult()).isNull();

        result = service.get(0L);
        assertThat(result.isCalculationFailed()).isTrue();
        assertThat(result.getResult()).isNull();
        ;
    }

    @Test(expected = NullPointerException.class)
    public void whenGetByIdNullThenNPE() {
        service.get((Long) null);
    }

    // DELETE by ID ===================================================================
    @Test
    public void deleteByExistIdSuccess() {
        groupType = new GroupType();
        groupType.setName(name1);

        Result<GroupType> result = service.save(groupType);
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

    // GET ALL ===========================================================
    @Test
    public void getAll() {
        Result<Set<GroupType>> resultAll = service.get();
        assertThat(resultAll.isCalculationFailed()).isFalse();
        long size = resultAll.getResult().size();

        groupType = new GroupType();
        groupType.setName(name1);
        Result<GroupType> result = service.save(groupType);
        assertThat(result.isCalculationFailed()).isFalse();

        resultAll = service.get();
        assertThat(resultAll.isCalculationFailed()).isFalse();
        long newSize = resultAll.getResult().size();

        assertThat(newSize).isEqualTo(size + 1);
    }

    @Test
    public void whenZeroModelsGetAllReturnEmptySet() {
        Result<Set<GroupType>> resultAll = service.get();

        assertThat(resultAll).isNotNull();
        assertThat(resultAll.isCalculationFailed()).isFalse();

        assertThat(resultAll.getResult()).isEqualTo(0);
    }

    // DELETE by NAME ==========================================================
    @Test
    public void deleteByExistNameSuccess() {
        groupType = new GroupType();
        groupType.setName(name1);

        Result<GroupType> result = service.save(groupType);
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

    // GET by NAME ==============================================================
    @Test
    public void getByExistNameSuccess() {
        groupType = new GroupType();
        groupType.setName(name1);

        Result<GroupType> result = service.save(groupType);
        assertThat(result.isCalculationFailed()).isFalse();

        result = service.get(name1);
        assertThat(result.isCalculationFailed()).isFalse();

        assertThat(result.getResult().getName()).isEqualTo(name1);
    }

    @Test
    public void getByNotExistNameFalse() {
        Result<GroupType> result = service.get(name1);
        assertThat(result.isCalculationFailed()).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void whenGetByNameNullThenNPE() {
        service.get((String) null);
    }

    // IS NAME EXIST ========================================================
    @Test
    public void isNameExistReturnTrueWhenNameExist() {
        groupType = new GroupType();
        groupType.setName(name1);

        groupType.setName(name1);
        assertThat(service.save(groupType).isCalculationFailed()).isFalse();

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