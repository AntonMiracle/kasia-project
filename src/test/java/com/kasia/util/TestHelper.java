package com.kasia.util;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestHelper<T> {
    public ConstraintValidatorContext mockConstraintValidatorContext() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext node = mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class);
        when(context.buildConstraintViolationWithTemplate(any(String.class))).thenReturn(builder);
        when(builder.addPropertyNode(any(String.class))).thenReturn(node);
        when(node.addConstraintViolation()).thenReturn(context);
        return context;
    }
    public void copyProductionValidationMessagesPropertiesForTesting() throws IOException {
        Path originalValidationMessagesProperties = Paths.get("src", "main", "resources", "ValidationMessages.properties");
        Path testValidationMessagesProperties = Paths.get("src", "test", "resources", "ValidationMessages.properties");
        Files.copy(originalValidationMessagesProperties, testValidationMessagesProperties, StandardCopyOption.REPLACE_EXISTING);
    }

    public int countFields(Object where, Class<?> typeOf) {
        int count = 0;
        for (Field field : where.getClass().getDeclaredFields()) {
            if (field.getType().equals(typeOf)) ++count;
        }
        return count;
    }

    public int countFields(Object where) {
        return where.getClass().getDeclaredFields().length;
    }

    public ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }

    public Map<String, String> mapErrorFieldsWithMsg(Set<ConstraintViolation<T>> errors) {
        Map<String, String> mapa = new HashMap<>();
        errors.forEach(el -> mapa.put(el.getPropertyPath().toString(), el.getMessage()));
        return mapa;
    }
}
