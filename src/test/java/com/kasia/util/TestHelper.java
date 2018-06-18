package com.kasia.util;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestHelper {

    public void copyProductionValidationMessagesPropertiesForTesting() throws IOException {
        Path originalValidationMessagesProperties = Paths.get("src", "main", "resources", "ValidationMessages.properties");
        Path testValidationMessagesProperties = Paths.get("src", "test", "resources", "ValidationMessages.properties");
        Files.copy(originalValidationMessagesProperties, testValidationMessagesProperties, StandardCopyOption.REPLACE_EXISTING);
    }

    public ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
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
}
