package com.kasia.controller.dto.validator;

import com.kasia.controller.dto.AddBudgetDTO;
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
public class BudgetDTOValidationIT {
    @Autowired
    private Validator validator;
    private AddBudgetDTO dto = new AddBudgetDTO();

    @Before
    public void before() {
        dto.setBalanceInit("0");
        dto.setCurrency("EUR");
        dto.setName("Nameeee");
        dto.setUserEmail("anton@gmail.com");
    }

    @Test
    public void balanceInitValid() {
        String valid1 = "0";
        String valid2 = "0.0";
        String valid3 = "0.";
        String valid4 = ".0";
        String valid5 = "0,0";
        String valid6 = " 0.0 ";
        String valid7 = "   0  .   0     ";
        String valid8 = "0000000";
        String valid9 = "0000.00000";
        String valid10 = "0000000000000001";
        String valid11 = "0001.0000001";
        String valid12 = "000000.000001";
        String valid13 = "000000.10000";
        String valid14 = "-1.01";
        String valid15 = "+1.01";

        dto.setBalanceInit(valid1);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid2);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid3);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid4);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid5);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid6);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid7);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid8);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid9);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid10);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid11);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid12);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid13);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid14);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setBalanceInit(valid15);
        assertThat(validator.validate(dto).size() == 0).isTrue();
    }

    @Test
    public void balanceInitInvalid() {
        String invalid1 = "0.000.000,0";
        String invalid2 = "0.000.000";
        String invalid3 = "0,000000.0";
        String invalid6 = ".000,000";
        String invalid7 = "000,000.";
        String invalid8 = "000,000,";
        String invalid9 = "--1.01";
        String invalid10 = "1-.01";
        String invalid11 = "1.-01";
        String invalid12 = "1.01-";
        String invalid13 = "-,0";
        String invalid14 = "+,0";

        dto.setBalanceInit(invalid1);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setBalanceInit(invalid2);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setBalanceInit(invalid3);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setBalanceInit(invalid6);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setBalanceInit(invalid7);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setBalanceInit(invalid8);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setBalanceInit(invalid9);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setBalanceInit(invalid10);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setBalanceInit(invalid11);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setBalanceInit(invalid12);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setBalanceInit(invalid13);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setBalanceInit(invalid14);
        assertThat(validator.validate(dto).size() == 0).isFalse();
    }

    @Test
    public void test() {
        String val1 = "0";

    }

    @Test
    public void test2() {
        String regex = "^[-+]?\\d+|[-+]?\\d+[.,]\\d+|[-+]?\\d+[.,]|[.,]\\d+$";
        String val1 = "";
        System.out.println("=======true=========");
        System.out.println(val1.matches(regex));
        System.out.println("=======false=========");
    }
}