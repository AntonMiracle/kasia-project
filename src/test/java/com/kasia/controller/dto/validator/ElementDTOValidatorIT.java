package com.kasia.controller.dto.validator;

import com.kasia.ModelTestData;
import com.kasia.controller.dto.ElementDTO;
import com.kasia.model.*;
import com.kasia.model.service.BudgetService;
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
public class ElementDTOValidatorIT {
    @Autowired
    private BudgetService bService;
    @Autowired
    private Validator validator;
    private ElementDTO dto = new ElementDTO();

    @Before
    public void before() {
        dto.setName("Valid Name");
        dto.setCurrency(Currencies.EUR.toString());
        dto.setDefaultPrice("0");
        dto.setType(ElementType.CONSUMPTION.toString());
    }

    @Test
    public void nameValid() {
        String valid1 = "Name2";
        String valid2 = "M1";
        String valid3 = "M 1";
        String valid4 = "Name1 Name1";
        String valid5 = "m1 M1 M1";
        StringBuilder valid6 = new StringBuilder();
        for (int i = 0; i < 64; ++i) valid6.append("s");
        String valid7 = "   m1    M1    M1    ";
        String valid8 = " M ";

        dto.setName(valid1);
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

    @Test
    public void ifNameNotUniqueNameInvalid() {
        Budget budget = ModelTestData.getBudget1();
        bService.saveBudget(budget);
        Element element = ModelTestData.getElement1();
        bService.addElement(budget.getId(), element);

        assertThat(bService.isElementUnique(budget.getId(), element.getName())).isFalse();
        dto.setBudgetId(budget.getId());
        dto.setName(element.getName());
        assertThat(validator.validate(dto).size() == 0).isFalse();

        bService.removeElement(budget.getId(), element.getId());
        assertThat(bService.isElementUnique(budget.getId(), element.getName())).isTrue();
        bService.deleteBudget(budget.getId());
        assertThat(bService.findAllBudgets().size() == 0).isTrue();
    }

    @Test
    public void priceValid() {
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

        dto.setDefaultPrice(valid1);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid2);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid3);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid4);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid5);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid6);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid7);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid8);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid9);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid10);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid11);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid12);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid13);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid14);
        assertThat(validator.validate(dto).size() == 0).isTrue();
        dto.setDefaultPrice(valid15);
        assertThat(validator.validate(dto).size() == 0).isTrue();
    }

    @Test
    public void priceInvalid() {
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

        dto.setDefaultPrice(invalid1);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setDefaultPrice(invalid2);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setDefaultPrice(invalid3);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setDefaultPrice(invalid6);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setDefaultPrice(invalid7);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setDefaultPrice(invalid8);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setDefaultPrice(invalid9);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setDefaultPrice(invalid10);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setDefaultPrice(invalid11);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setDefaultPrice(invalid12);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setDefaultPrice(invalid13);
        assertThat(validator.validate(dto).size() == 0).isFalse();
        dto.setDefaultPrice(invalid14);
        assertThat(validator.validate(dto).size() == 0).isFalse();
    }
}
