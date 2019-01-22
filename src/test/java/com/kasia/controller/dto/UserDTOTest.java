package com.kasia.controller.dto;

import com.kasia.ModelTestData;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDTOTest {

    private boolean isValid(UserDTO dto) {
        ValidatorFactory factory = javax.validation.Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        System.out.println(violations);
        return violations.size() == 0;
    }

    @Test
    public void confirmPassword() {
        UserDTO dto1 = ModelTestData.getUserDto1();
        UserDTO dto2 = ModelTestData.getUserDto1();
        UserDTO dto3 = ModelTestData.getUserDto1();
        dto2.setConfirm(dto2.getPassword() + "new");
        dto3.setPassword(dto3.getConfirm() + "new");

        assertThat(dto1.getPassword()).isEqualTo(dto1.getConfirm());
        assertThat(dto2.getPassword()).isNotEqualTo(dto2.getConfirm());
        assertThat(dto3.getPassword()).isNotEqualTo(dto3.getConfirm());
        assertThat(isValid(dto1)).isTrue();
        assertThat(isValid(dto2)).isFalse();
        assertThat(isValid(dto3)).isFalse();
    }

}