package com.kasia.core.validator;

import com.kasia.config.AppConfig;
import com.kasia.core.TestHelper;
import com.kasia.core.model.GroupType;
import com.kasia.core.model.Result;
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
public class GroupTypeValidatorServiceTest implements TestHelper<GroupType> {

    private GroupType groupType;
    @Autowired
    private ValidatorService<GroupType> validator;

    @Before
    public void before() {
        assert validator != null;
        groupType = new GroupType();
    }

    //validation ===================================================
    @Test
    public void nameWithEmptyStringInvalid() {
        groupType.setName("");

        Result<Boolean> result = validator.validation(groupType);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult()).isFalse();
    }

    @Test
    public void nameWithLengthMoreThan32Invalid() {
        groupType.setName("ggggggggggggggggggggggggggggggggG");

        Result<Boolean> result = validator.validation(groupType);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult()).isFalse();
    }

    @Test
    public void nameWithNullInvalid() {
        groupType.setName(null);

        Result<Boolean> result = validator.validation(groupType);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult()).isFalse();
    }

    @Test(expected = NullPointerException.class)
    public void whenValidationWithNullThenNPE() {
        validator.validation(null);
    }

    //mapping error by field name and error msg =============================================
    @Test
    public void groupHasNameWithNullMapDieldMsgWithHasSize1() {
        groupType.setName(null);

        Set<ConstraintViolation<GroupType>> errors = validator.getValidator().validate(groupType);
        Result<Map<String, String>> result = validator.mapFieldMsg(errors);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult().size()).isEqualTo(1);
    }

    @Test(expected = NullPointerException.class)
    public void whenMapFieldMsgWithNullThenNPE() {
        validator.mapFieldMsg(null);
    }

    // eliminate null value =====================================================================
    @Test
    public void setAcceptDefaultValueSetIsNullFalse() {
        groupType = new GroupType();
        assertThat(groupType.getName()).isNull();

        Result<GroupType> result = validator.eliminateNull(groupType);

        assertThat(result.isCalculationFailed()).isFalse();
        assertThat(result.getResult().getName()).isNotNull();
    }

    @Test(expected = NullPointerException.class)
    public void whenSetAcceptDefaultWithNullThenNPE() {
        validator.eliminateNull(null);
    }
}