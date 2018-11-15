package com.kasia.service.validation.message;

import com.kasia.ConfigurationEjbCdiContainerForIT;
import com.kasia.model.Budget;
import com.kasia.service.validation.BudgetValidationService;
import com.kasia.service.validation.field.BField;
import com.kasia.service.validation.field.ModelField;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BMessageLinkIT extends ConfigurationEjbCdiContainerForIT {
    @Inject
    private BudgetValidationService validationService;

    @Test
    public void allLinkReturnMessage() {
        Budget budget = new Budget();
        budget.setId(-10L);
        Map<ModelField, String> errors = validationService.getMessages(budget, BField.values());

        for (BField field : BField.values()) {
            assertThat(errors.get(field)).isNotNull();
            assertThat(errors.get(field).trim().startsWith("{")).isFalse();
            assertThat(errors.get(field).trim().length() > 0).isTrue();
        }
    }
}