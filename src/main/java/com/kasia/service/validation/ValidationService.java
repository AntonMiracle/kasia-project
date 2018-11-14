package com.kasia.service.validation;

import com.kasia.exception.FieldNotExistRuntimeException;
import com.kasia.model.Model;
import com.kasia.service.Service;
import com.kasia.service.validation.field.ModelField;
import com.kasia.service.validation.message.ModelValidationMessageLink;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface ValidationService<M extends Model, F extends ModelField, L extends ModelValidationMessageLink> extends Service {

    default boolean isValid(M model) throws NullPointerException {
        if (model == null) throw new NullPointerException();
        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }

    default String getErrorMsgByField(M model, F field) {
        StringBuilder result = new StringBuilder("");
        Map<ModelField, String> all = getAllErrorValidationMessage(model, field);
        if (all.get(field) != null) result.append(all.get(field));
        return result.toString();
    }

    default boolean isValid(M model, F field) {
        return getAllErrorValidationMessage(model, field).get(field) == null;
    }

    default ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }

    default Map<ModelField, String> getAllErrorValidationMessage(M model, F field) {
        Map<ModelField, String> result = new HashMap<>();

        try (ValidatorFactory factory = getValidatorFactory()) {
            Set<ConstraintViolation<M>> validate = factory.getValidator().validate(model);
            for (ConstraintViolation<M> c : validate) {

                for (ModelField f : field.getAll()) {
                    if (c.getPropertyPath().toString().equals(f.getName())) {
                        result.put(f, c.getMessage());
                        break;
                    }
                }
            }
        }
        return result;
    }

    default void addConstraintViolation(F field, L errorMsgLink, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(errorMsgLink.getLink())
                .addPropertyNode(field.getName())
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }

    boolean isValueValid(F field, Object value) throws NullPointerException, FieldNotExistRuntimeException;

    String getErrorMsgByValue(F field, Object value) throws NullPointerException, FieldNotExistRuntimeException;


}
