package com.kasia.service.validation.message;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Operation;
import com.kasia.service.validation.OperationValidationService;
import com.kasia.service.validation.field.ModelField;
import com.kasia.service.validation.field.OField;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OMessageLinkIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private OperationValidationService validationService;

    @Test
    public void allLinkReturnMessage() {
        Operation operation = new Operation();
        operation.setId(-10L);
        Map<ModelField, String> errors = validationService.getMessages(operation, OField.values());

        for (OField field : OField.values()) {
            assertThat(errors.get(field)).isNotNull();
            assertThat(errors.get(field).trim().startsWith("{")).isFalse();
            assertThat(errors.get(field).trim().length() > 0).isTrue();
        }
    }

}