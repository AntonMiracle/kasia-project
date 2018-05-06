package com.kasia.core.validator;

import com.kasia.config.AppConfig;
import com.kasia.core.TestHelper;
import com.kasia.core.model.Role;
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
public class RoleValidatorServiceTest implements TestHelper<Role> {
    @Autowired
    private ValidatorService<Role> validator;
    private Role role;

    @Before
    public void before() {
        assert validator != null;
        role = new Role();
        role.setName("Name");
    }

    //name ---------------------------------------
    @Test
    public void nameWithEmptyStringInvalid() {
        role.setName("");
        assertThat(validator.isValid(role)).isFalse();
    }

    @Test
    public void nameWithLengthMoreThan32Invalid() {
        role.setName("ggggggggggggggggggggggggggggggggG");
        assertThat(validator.isValid(role)).isFalse();
    }

    @Test
    public void nameWithNullInvalid() {
        role.setName(null);
        assertThat(validator.isValid(role)).isFalse();
    }

    //id ---------------------------------------------------
    @Test
    public void idWithNullValid() throws NoSuchFieldException, IllegalAccessException {
        assertThat(role.getId()).isNull();
        assertThat(validator.isValid(role)).isTrue();
    }

    //mapping error by field name and error msg -----------------------------------------
    @Test
    public void groupHasNameWithNullMapWithErrorHasSize1() {
        role.setName(null);
        assertThat(validator.mappingFieldMsg(role).size()).isEqualTo(1);
    }
}
