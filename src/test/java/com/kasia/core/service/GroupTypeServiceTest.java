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
public class GroupTypeServiceTest implements TestHelper {
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
    public void saveOrUpdateValidGroupType() {
        groupType.setName(getGroupTypeNameForTesting1());
        assertThat(groupType.getId()).isNull();

        groupType = service.saveOrUpdate(groupType);
        assertThat(groupType.getId()).isNotNull();
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenSaveOrUpdateGroupTypeWithInValidNameThenCVE() {
        groupType.setName("");
        assertThat(groupType.getId()).isNull();

        groupType = service.saveOrUpdate(groupType);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenSaveOrUpdateGroupTypeWithNullNameThenCVE() {
        groupType.setName(null);
        assertThat(groupType.getId()).isNull();

        groupType = service.saveOrUpdate(groupType);
    }

    @Test(expected = NullPointerException.class)
    public void whenSaveOrUpdateGroupTypeWithNullThenNPE() {
        groupType = service.saveOrUpdate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSaveOrUpdateGroupTypeWithIllegalNameThenIAE() {
        groupType.setName(getGroupTypeNameForTesting1());
        service.saveOrUpdate(groupType);

        GroupType illegalGroupType = new GroupType();
        illegalGroupType.setName(getGroupTypeNameForTesting1());
        service.saveOrUpdate(illegalGroupType);
    }

    // GET by ID --------------------------------------------------------

}