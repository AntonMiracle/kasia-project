package com.kasia.core.service;

import com.kasia.config.AppConfig;
import com.kasia.core.TestHelper;
import com.kasia.core.model.GroupType;
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
public class GroupTypeServiceTest implements TestHelper<GroupType> {
    @Autowired
    private GroupTypeService service;
    private GroupType groupType;

    @Before
    public void before() {
        assert service != null;
        groupType = new GroupType();
    }

    @After
    public void after() {
        if (service.isNameExist(getGroupTypeNameForTesting1())) {
            service.delete(getGroupTypeNameForTesting1());
        }
        if (service.isNameExist(getGroupTypeNameForTesting2())) {
            service.delete(getGroupTypeNameForTesting2());
        }
    }

    // SAVE OR UPDATE --------------------------------------------------------
    @Test
    public void saveOrUpdate() {
        groupType.setName(getGroupTypeNameForTesting1());
        assertThat(groupType.getId()).isNull();

        groupType = service.saveOrUpdate(groupType);
        assertThat(groupType.getId()).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSaveOrUpdateWithNotUniqueNameThenIAE() {
        groupType.setName(getGroupTypeNameForTesting1());
        service.saveOrUpdate(groupType);

        groupType = new GroupType();
        groupType.setName(getGroupTypeNameForTesting1());
        service.saveOrUpdate(groupType);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenSaveOrUpdateWithInValidNameThenCVE() {
        groupType.setName("");
        assertThat(groupType.getId()).isNull();

        groupType = service.saveOrUpdate(groupType);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenSaveOrUpdateWithNullNameThenCVE() {
        groupType.setName(null);
        assertThat(groupType.getId()).isNull();

        groupType = service.saveOrUpdate(groupType);
    }

    @Test(expected = NullPointerException.class)
    public void whenSaveOrUpdateWithNullThenNPE() {
        groupType = service.saveOrUpdate(null);
    }

    // GET by ID --------------------------------------------------------
    @Test
    public void getById() {
        groupType.setName(getGroupTypeNameForTesting1());
        Long id = service.saveOrUpdate(groupType).getId();

        groupType = service.get(id);

        assertThat(groupType.getId()).isEqualTo(id);
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
        groupType.setName(getGroupTypeNameForTesting1());
        Long id = service.saveOrUpdate(groupType).getId();

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

        groupType.setName(getGroupTypeNameForTesting1());
        service.saveOrUpdate(groupType).getName();

        assertThat(service.get().size()).isEqualTo(size + 1);

    }

    // DELETE by NAME --------------------------------------------------------
    @Test
    public void deleteByName() {
        groupType.setName(getGroupTypeNameForTesting1());
        String name = service.saveOrUpdate(groupType).getName();

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
        groupType.setName(getGroupTypeNameForTesting1());
        String name = service.saveOrUpdate(groupType).getName();

        groupType = service.get(name);

        assertThat(groupType.getName()).isEqualTo(name);
    }

    @Test(expected = NullPointerException.class)
    public void whenGetByNameWithNullThenNPE() {
        service.get((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetByNameWithNotExistNameThenIAE() {
        service.get("-10L");
    }

    // IS NAME EXIST--------------------------------------------------------
    @Test
    public void isNameExistWithExistingName() {
        groupType.setName(getGroupTypeNameForTesting1());
        service.saveOrUpdate(groupType).getName();

        assertThat(service.isNameExist(getGroupTypeNameForTesting1())).isTrue();
    }

    @Test
    public void isNameExistWithNonExistingName() {
        assertThat(service.isNameExist(getGroupTypeNameForTesting1())).isFalse();
    }

    @Test(expected = NullPointerException.class)
    public void isNameExistWithNull() {
        service.isNameExist(null);
    }

}