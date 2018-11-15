package com.kasia.service.validation;

import com.kasia.exception.FieldNotExistRuntimeException;
import com.kasia.model.Model;
import com.kasia.service.Service;
import com.kasia.service.validation.field.ModelField;
import com.kasia.service.validation.message.ModelValidationMessageLink;
import com.kasia.service.validation.regex.ModelRegex;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public interface ValidationService<M extends Model, F extends ModelField, L extends ModelValidationMessageLink, R extends ModelRegex> extends Service {

    default boolean isValid(M model) throws NullPointerException {
        if (model == null) throw new NullPointerException();
        try (ValidatorFactory factory = getValidatorFactory()) {
            return factory.getValidator().validate(model).size() == 0;
        }
    }

    default String getMessage(M model, F field) {
        StringBuilder result = new StringBuilder("");
        Map<ModelField, String> errors = getMessages(model, field);
        if (errors.get(field) != null) result.append(errors.get(field));
        return result.toString();
    }

    default boolean isValid(M model, F field) {
        return getMessages(model, field).get(field) == null;
    }

    default ValidatorFactory getValidatorFactory() {
        return Validation.buildDefaultValidatorFactory();
    }

    default Map<ModelField, String> getMessages(M model, ModelField... fields) {
        Map<ModelField, String> result = new HashMap<>();
        if (fields.length == 0) return result;

        try (ValidatorFactory factory = getValidatorFactory()) {
            Set<ConstraintViolation<M>> validate = factory.getValidator().validate(model);
            for (ConstraintViolation<M> c : validate) {

                for (ModelField f : fields) {
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

    M createModelWithFieldAndValue(F field, Object value) throws FieldNotExistRuntimeException;

    default boolean isValid(F field, Object value) throws NullPointerException, FieldNotExistRuntimeException {
        return isValid(createModelWithFieldAndValue(field, value), field);
    }

    default String getMessage(F field, Object value) throws NullPointerException, FieldNotExistRuntimeException {
        return getMessage(createModelWithFieldAndValue(field, value), field);
    }

    default boolean isMatch(String value, R regex) {
        return value != null && Pattern.compile(regex.getREGEX()).matcher(value).matches();
    }

}
