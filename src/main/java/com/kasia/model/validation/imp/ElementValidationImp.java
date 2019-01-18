package com.kasia.model.validation.imp;

import com.kasia.model.Element;
import com.kasia.model.validation.ElementValidation;
import com.kasia.model.validation.FieldName;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class ElementValidationImp implements ElementValidation {
    @Override
    public boolean isNameValid(String name) {
        Element element = new Element();
        element.setName(name);

        Validator validator = getValidator();
        for (ConstraintViolation<Element> violation : validator.validate(element)) {
            if (violation.getPropertyPath().toString().equals(FieldName.ELEMENT_NAME.getName())) {
                return false;
            }
        }
        return true;
    }
}
