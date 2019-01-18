package com.kasia.model.validation.imp;

import com.kasia.model.ElementProvider;
import com.kasia.model.validation.ElementProviderValidationService;
import com.kasia.model.validation.FieldName;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class ElementProviderValidationServiceImp implements ElementProviderValidationService {
    @Override
    public boolean isNameValid(String name) {
        ElementProvider provider = new ElementProvider();
        provider.setName(name);

        Validator validator = getValidator();
        for (ConstraintViolation<ElementProvider> violation : validator.validate(provider)) {
            if (violation.getPropertyPath().toString().equals(FieldName.ELEMENT_PROVIDER_NAME.getName())) {
                return false;
            }
        }
        return true;
    }
}
