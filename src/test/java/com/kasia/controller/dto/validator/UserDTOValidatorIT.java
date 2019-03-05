package com.kasia.controller.dto.validator;

import com.kasia.ModelTestData;
import com.kasia.controller.dto.UserDTO;
import com.kasia.model.User;
import com.kasia.model.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDTOValidatorIT {
    @Autowired
    private UserService uService;
    @Autowired
    private Validator validator;
    private UserDTO dto = new UserDTO();
    private User user;

    @Before
    public void before() {
        user = uService.saveUser(ModelTestData.getUser1());

        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPassword(user.getPassword());
        dto.setConfirm(user.getPassword());
        dto.setZoneId(user.getZoneId().toString());
        dto.setLang(user.getLocale().getLanguage());
        dto.setCountry(user.getLocale().getCountry());
    }

    @After
    public void after() {
        uService.findAllUsers().forEach(u -> uService.deleteUser(u.getId()));
    }


    @Test
    public void nameValid() {
        String valid1 = "Name";
        String valid2 = "M1";
        String valid3 = "M 1";
        String valid4 = "Name1 Name1";
        String valid5 = "m1 M1 M1";
        StringBuilder valid6 = new StringBuilder();
        for (int i = 0; i < 64; ++i) valid6.append("s");
        String valid7 = "   m1    M1    M1    ";
        String valid8 = " M ";

        dto.setName(valid1);
        System.out.println("getName : " + dto.getName());
        System.out.println(validator.validate(dto));
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setName(valid2);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setName(valid3);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setName(valid4);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setName(valid5);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setName(valid6.toString());
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setName(valid7);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setName(valid8);
        assertThat(validator.validate(dto).size() == 0).isTrue();
    }

    @Test
    public void nameInvalid() {
        String invalid1 = "";
        String invalid2 = null;
        StringBuilder invalid3 = new StringBuilder();
        for (int i = 0; i < 65; ++i) invalid3.append("s");

        dto.setName(invalid1);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setName(invalid2);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setName(invalid3.toString());
        assertThat(validator.validate(dto).size() == 0).isFalse();
    }

}