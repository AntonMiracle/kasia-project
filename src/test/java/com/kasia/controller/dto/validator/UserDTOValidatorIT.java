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

        dto.setEmail("new" + user.getEmail());
        dto.setName(user.getName() + user.getName());
        dto.setPassword(ModelTestData.getUser1().getPassword());
        dto.setConfirm(dto.getPassword());
        dto.setZoneId(user.getZoneId().toString());
        dto.setLang(user.getLocale().getLanguage());
        dto.setCountry(user.getLocale().getCountry());
        assertThat(validator.validate(dto).size() == 0).isTrue();
    }

    @After
    public void after() {
        uService.findAllUsers().forEach(u -> uService.deleteUser(u.getId()));
    }


    @Test
    public void nameValid() {
        String valid1 = "Name1222";
        String valid2 = "M1";
        String valid3 = "M 1";
        String valid4 = "Name1 Name1";
        String valid5 = "m1 M1 M1";
        StringBuilder valid6 = new StringBuilder();
        for (int i = 0; i < 64; ++i) valid6.append("s");
        String valid7 = "   m1    M1    M1    ";
        String valid8 = " M ";
        String valid9 = "Anton";

        dto.setName(valid1);
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
        dto.setName(valid9);
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

    @Test
    public void emailValid() {
        StringBuilder valid1 = new StringBuilder();
        valid1.append("anton@");
        while (valid1.length() < 64) valid1.append("s");
        String valid2 = "   naton@gmail.com   ";
        String valid3 = "   n a t o n @ gm a i l   .     com   ";
        String valid4 = "   n .a. t. o .n @ g.m. a .i l   .     com   ";

        dto.setEmail(valid1.toString());
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setEmail(valid2);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setEmail(valid3);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setEmail(valid4);
        assertThat(validator.validate(dto).size() == 0).isTrue();
    }

    @Test
    public void emailInvalid() {
        StringBuilder invalid1 = new StringBuilder();
        invalid1.append("anton@");
        while (invalid1.length() < 65) invalid1.append("s");
        String invalid2 = "a@";
        String invalid3 = "anton.gmail.com";
        String invalid4 = null;

        dto.setEmail(invalid1.toString());
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setEmail(invalid2);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setEmail(invalid3);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setEmail(invalid4);
        assertThat(validator.validate(dto).size() == 0).isFalse();
    }

    @Test
    public void passwordValid() {
        StringBuilder valid1 = new StringBuilder();
        valid1.append("A2c");
        while (valid1.length() < 64) valid1.append("s");
        String valid2 = "Pas2";

        dto.setPassword(valid1.toString());
        dto.setConfirm(valid1.toString());
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setPassword(valid2);
        dto.setConfirm(valid2);
        assertThat(validator.validate(dto).size() == 0).isTrue();
    }

    @Test
    public void passwordInvalid() {
        StringBuilder invalid1 = new StringBuilder();
        invalid1.append("A2c");
        while (invalid1.length() < 65) invalid1.append("s");
        String invalid2 = " Password2 ";

        dto.setPassword(invalid1.toString());
        dto.setConfirm(invalid1.toString());
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setPassword(invalid2);
        dto.setConfirm(invalid2);
        assertThat(validator.validate(dto).size() == 0).isFalse();
    }

    @Test
    public void confirmPassValid() {
        String valid1 = "Password2";

        dto.setPassword(valid1);
        dto.setConfirm(valid1);
        assertThat(validator.validate(dto).size() == 0).isTrue();
    }

    @Test
    public void confirmPassInvalid() {
        String invalid1 = "Password2";

        dto.setPassword(invalid1);
        dto.setConfirm(invalid1 + invalid1);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setPassword(invalid1);
        dto.setConfirm(null);
        assertThat(validator.validate(dto).size() == 0).isFalse();
    }
}