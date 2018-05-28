package com.kasia.core.validator;

import com.kasia.config.AppConfig;
import com.kasia.core.TestHelper;
import com.kasia.core.model.Result;
import com.kasia.core.model.Role;
import com.kasia.core.service.ValidatorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class RoleValidatorServiceTest implements TestHelper<Role> {
    @Autowired
    private ValidatorService<Role> validator;
    private Role role;
    private String name1 = getRoleNameForTesting1();

    @Before
    public void before() {
        assert validator != null;
    }

    // VALIDATION ==============================================
    @Test
    public void nameWithEmptyStringInvalid() {
        role = new Role();
        role.setName("");

        Result<Boolean> result = validator.validation(role);
        assertThat(result.isCalculationFailed()).isFalse();

        assertThat(result.getResult()).isFalse();
    }

    @Test
    public void nameWithLengthMoreThan32Invalid() {
        role = new Role();
        role.setName("ggggggggggggggggggggggggggggggggG");

        Result<Boolean> result = validator.validation(role);
        assertThat(result.isCalculationFailed()).isFalse();

        assertThat(result.getResult()).isFalse();
    }

    @Test
    public void nameWithNullInvalid() {
        role = new Role();
        role.setName(null);

        Result<Boolean> result = validator.validation(role);
        assertThat(result.isCalculationFailed()).isFalse();

        assertThat(result.getResult()).isFalse();
    }

    @Test(expected = NullPointerException.class)
    public void whenValidationNullThenNPE() {
        validator.validation(null);
    }

    // MAPPING FIELDS MSG =====================================================
    @Test
    public void nameWithNullMapFieldMsgHasSizeOne() {
        role = new Role();
        role.setName(null);

        Set<ConstraintViolation<Role>> errors = validator.getValidator().validate(role);
        Result<Map<String, String>> result = validator.mapFieldMsg(errors);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult().size()).isEqualTo(1);
    }

    @Test(expected = NullPointerException.class)
    public void whenMapFieldMsgWithNullThenNPE() {
        validator.mapFieldMsg(null);
    }

    // ELIMINATE NULL =======================================================
    @Test
    public void eliminateNullSucess() {
        role = new Role();
        assertThat(role.getName()).isNull();

        Result<Role> result = validator.eliminateNull(role);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult().getName()).isNotNull();
    }

    @Test(expected = NullPointerException.class)
    public void whenSetAcceptDefaultWithNullThenNPE() {
        validator.eliminateNull(null);
    }

}
