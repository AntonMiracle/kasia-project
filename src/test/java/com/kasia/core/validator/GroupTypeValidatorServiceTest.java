package com.kasia.core.validator;

import com.kasia.config.AppConfig;
import com.kasia.core.TestHelper;
import com.kasia.core.model.GroupType;
import com.kasia.core.service.ValidatorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class GroupTypeValidatorServiceTest implements TestHelper<GroupType> {

    private GroupType groupType;
    @Autowired
    private ValidatorService<GroupType> validator;

    @Before
    public void before() {
        assert validator != null;
        groupType = new GroupType();
        groupType.setName("Name");
        assert validator != null;
    }

    //name ----------------------------------------------------------
    @Test
    public void nameWithEmptyStringInvalid() {
        groupType.setName("");
        assertThat(validator.isValid(groupType)).isFalse();
    }

    @Test
    public void nameWithLengthMoreThan32Invalid() {
        groupType.setName("ggggggggggggggggggggggggggggggggG");
        assertThat(validator.isValid(groupType)).isFalse();
    }

    @Test
    public void nameWithNullInvalid() {
        groupType.setName(null);
        assertThat(validator.isValid(groupType)).isFalse();
    }

    //id ----------------------------------------------------------
    @Test
    public void idWithNullValid() throws NoSuchFieldException, IllegalAccessException {
        assertThat(groupType.getId()).isNull();
        assertThat(validator.isValid(groupType)).isTrue();
    }

    //mapping error by field name and error msg -----------------------------------------
    @Test
    public void groupHasNameWithNullMapWithErrorHasSize1() {
        groupType.setName(null);
        assertThat(validator.mappingFieldMsg(groupType).size()).isEqualTo(1);
    }
}