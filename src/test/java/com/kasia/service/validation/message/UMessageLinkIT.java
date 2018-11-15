package com.kasia.service.validation.message;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.User;
import com.kasia.service.validation.UserValidationService;
import com.kasia.service.validation.field.ModelField;
import com.kasia.service.validation.field.UField;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UMessageLinkIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private UserValidationService validationService;

    @Test
    public void allLinkReturnMessage() {
        User user = new User();
        user.setId(-10L);
        Map<ModelField, String> errors = validationService.getMessages(user, UField.values());

        for (UField field : UField.values()) {
            assertThat(errors.get(field)).isNotNull();
            assertThat(errors.get(field).trim().startsWith("{")).isFalse();
            assertThat(errors.get(field).trim().length() > 0).isTrue();
        }
    }
}