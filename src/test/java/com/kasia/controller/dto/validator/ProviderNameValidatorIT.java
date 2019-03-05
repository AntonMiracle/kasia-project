package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.ProviderDTO;
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
public class ProviderNameValidatorIT {
    @Autowired
    private Validator validator;
    private ProviderDTO dto = new ProviderDTO();

    @Before
    public void before() {
        dto.setName("Valid Name");
        dto.setDescription("valid description");
    }

    @Test
    public void nameValid() {
        String valid1 = "Name";
        String valid2 = "M1";
        String valid3 = "M 1";
        String valid4 = "Name1 Name1";
        String valid5 = "m1 M1 M1";
        StringBuilder valid6 = new StringBuilder();
        for(int i = 0; i < 32; ++i) valid6.append("s");
        String valid7 = "   m1    M1    M1    ";
        String valid8 = " M ";

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
    }

    @Test
    public void nameInvalid() {
        String invalid1 = "";
        String invalid2 = null;
        StringBuilder invalid3 = new StringBuilder();
        for(int i = 0; i < 65;++i) invalid3.append("s");

        dto.setName(invalid1);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setName(invalid2);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setName(invalid3.toString());
        assertThat(validator.validate(dto).size() == 0).isFalse();
    }

    @Test
    public void descriptionValid() {
        String valid1 = "";
        String valid2 = null;
        String valid3 = "n";
        String valid4 = " n   nnn  m  ";
        StringBuilder valid5 = new StringBuilder();
        for (int i = 0; i < 250; ++i) valid5.append("s");

        dto.setDescription(valid1);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDescription(valid2);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDescription(valid3);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDescription(valid4);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDescription(valid5.toString());
        assertThat(validator.validate(dto).size() == 0).isTrue();
    }

    @Test
    public void descriptionInvalid() {
        StringBuilder invalid2 = new StringBuilder();
        for (int i = 0; i < 251; ++i) invalid2.append("s");

        dto.setDescription(invalid2.toString());
        System.out.println(invalid2.length());
        System.out.println(validator.validate(dto));
        assertThat(validator.validate(dto).size() == 0).isFalse();
    }

    @Test
    public void descriptionValidationMakeTrim() {
        String des = "    name    with  space         duplication     ";
        dto.setDescription(des);
        assertThat(dto.getDescription().matches("^\\s.*$")).isTrue();
        assertThat(dto.getDescription().matches("^.*\\s$")).isTrue();
        assertThat(dto.getDescription().matches("^.*\\s{2,}.*$")).isTrue();

        validator.validate(dto);
        assertThat(dto.getDescription().matches("^\\s.*$")).isFalse();
        assertThat(dto.getDescription().matches("^.*\\s$")).isFalse();
        assertThat(dto.getDescription().matches("^.*\\s{2,}.*$")).isFalse();
    }
}