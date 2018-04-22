package com.kasia.core.service.validator;

import com.kasia.core.model.Role;
import com.kasia.core.service.validator.impl.RoleValidatorServiceImpl;
import org.assertj.core.api.Java6Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RoleValidatorServiceTestValidator implements HelpTestingValidator<Role> {
    private Role role;
    private RoleValidatorService validatorService;

    @Before
    public void before() {
        validatorService = new RoleValidatorServiceImpl();
        role = new Role();
    }

    private String nameField = getField(Role.class, "name");

    @Test
    public void nameWithNullInvalid() {
        role.setName(null);
        assertThat(countConstraintViolation(validatorService, role, nameField) == 1).isTrue();
    }

    @Test
    public void nameWithLengthMoreThan16Invalid() {
        role.setName("useruseruseruserR");
        assertThat(countConstraintViolation(validatorService, role, nameField) == 1).isTrue();
    }

    @Test
    public void nameWithLengthLessThan4Invalid() {
        role.setName("use");
        assertThat(countConstraintViolation(validatorService, role, nameField) == 1).isTrue();
    }

    @Test
    public void nameWithWhiteSymbolsFromStartInvalid() {
        role.setName("  use");
        assertThat(countConstraintViolation(validatorService, role, nameField) == 1).isTrue();
    }

    @Test
    public void nameWithWhiteSymbolsInTheEndInvalid() {
        role.setName("use  ");
        assertThat(countConstraintViolation(validatorService, role, nameField) == 1).isTrue();
    }

    @Test
    public void nameWithNotOnlyAlphabetSymbolInvalid() {
        role.setName("_-u-se_0r_$");
        assertThat(countConstraintViolation(validatorService, role, nameField) == 1).isTrue();
    }

    @Test
    public void validatorTrimRoleFields() {
        role.setName(" Name  ");
        validatorService.trimFields(role);
        assertThat(countConstraintViolation(validatorService, role, nameField) == 0).isTrue();
    }

    @Test
    public void roleWithValidFields() {
        role.setName("Name");
        assertThat(validatorService.isValid(role)).isTrue();
    }

    @Test
    public void roleHas2Fields() {
        Java6Assertions.assertThat(validatorService.countFields(role)).isEqualTo(2);
    }
}
