package com.kasia.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestHelper {

    /**
     * Make copy ValidationMessages.properties from main/resources to test/resources
     *
     * @throws IOException
     */
    public void copyForTestValidationMessagesProperties() throws IOException {
        Path originalValidationMessagesProperties = Paths.get("src", "main", "resources", "ValidationMessages.properties");
        Path testValidationMessagesProperties = Paths.get("src", "test", "resources", "ValidationMessages.properties");
        Files.copy(originalValidationMessagesProperties, testValidationMessagesProperties, StandardCopyOption.REPLACE_EXISTING);
    }
}
