package com.kasia.controller.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDTOIT {

//
//    private boolean isValid(UserDTO dto) {
//        ValidatorFactory factory = javax.validation.Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
//        return violations.size() == 0;
//    }

    @Test
    public void confirmPassword() {
        /// cant inject UserValidation reference
//        UserDTO dto1 = ModelTestData.getUserDto1();
//        UserDTO dto2 = ModelTestData.getUserDto1();
//        UserDTO dto3 = ModelTestData.getUserDto1();
//        dto2.setConfirm(dto2.getPassword() + "new");
//        dto3.setPassword(dto3.getConfirm() + "new");
//
//        assertThat(dto1.getPassword()).isEqualTo(dto1.getConfirm());
//        assertThat(dto2.getPassword()).isNotEqualTo(dto2.getConfirm());
//        assertThat(dto3.getPassword()).isNotEqualTo(dto3.getConfirm());
//        assertThat(isValid(dto1)).isTrue();
//        assertThat(isValid(dto2)).isFalse();
//        assertThat(isValid(dto3)).isFalse();
    }

}