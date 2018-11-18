package com.kasia.validation;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ValidationServiceTest {
    @Test
    public void isExist() throws Exception {
        ValidationService validationService = new ValidationService();
        for (Field f : validationService.getClass().getDeclaredFields()) {
            assertThat(validationService.isExist(f.get(validationService).toString())).isTrue();
        }
    }

}