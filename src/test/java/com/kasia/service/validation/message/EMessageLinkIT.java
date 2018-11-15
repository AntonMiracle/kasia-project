package com.kasia.service.validation.message;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Employer;
import com.kasia.service.validation.ValidationService;
import com.kasia.service.validation.field.EField;
import com.kasia.service.validation.field.ModelField;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EMessageLinkIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private ValidationService<Employer, EField, EMessageLink> validationService;

    @Test
    public void allLinkReturnMessage() {
        Employer employer = new Employer();
        employer.setId(-10L);
        Map<ModelField, String> errors = validationService.getMessages(employer, EField.values());

        for (EField field : EField.values()) {
            assertThat(errors.get(field)).isNotNull();
            assertThat(errors.get(field).trim().startsWith("{")).isFalse();
            assertThat(errors.get(field).trim().length() > 0).isTrue();
        }
    }

}